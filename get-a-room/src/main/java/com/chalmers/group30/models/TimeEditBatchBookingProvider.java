package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.utilities.CacheUpdateProvider;
import com.chalmers.group30.models.utilities.RoomToName;
import com.chalmers.group30.models.utilities.RoomToTimeEditID;
import com.google.common.collect.Lists;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class TimeEditBatchBookingProvider implements CacheUpdateProvider<Dictionary<Room, List<Booking>>> {

    private final RoomServiceInterface roomServiceInterface;
    private final TimeEditAPIInterface timeEditAPIInterface;

    private final int weeksForwardToCache = 2;
    private final int batchSize = 20;

    @Autowired
    public TimeEditBatchBookingProvider(RoomServiceInterface roomServiceInterface, TimeEditAPIInterface timeEditAPIInterface) {
        this.roomServiceInterface = roomServiceInterface;
        this.timeEditAPIInterface = timeEditAPIInterface;
    }


    @Override
    public Dictionary<Room, List<Booking>> getNewDataToCache() throws IOException {
        Dictionary<Room, List<Booking>> bookings = new Hashtable<>();
        try {
            return getBookings(roomServiceInterface.getRooms(), LocalDateTime.now(ZoneId.of("Europe/Paris")));
        } catch (ParserException e) {
            e.printStackTrace();
        }catch (IOException e){

        }
        return bookings;
    }

    private Dictionary<Room, List<Booking>> getBookings(List<Room> rooms, LocalDateTime startTime) throws IOException, ParserException {
        // Use hashtable explicitly in order to be able to use containsKey method and return Dictionary type
        Hashtable<Room, List<Booking>> bookings = new Hashtable<>();

        if (weeksForwardToCache <= 0){
            throw new IllegalArgumentException(weeksForwardToCache + " is not a valid number of weeks to cache");
        }

        List<List<Room>> roomBatches = Lists.partition(rooms, batchSize);

        LocalDateTime endTime = startTime.plusWeeks(weeksForwardToCache);

        for(List<Room> partition : roomBatches) {
            List<String> roomIDs = Lists.transform(partition, RoomToTimeEditID.INSTANCE);
            List<String> roomNames = Lists.transform(partition, RoomToName.INSTANCE);
            Calendar schedule = timeEditAPIInterface.getSchedule(roomIDs, startTime, endTime);

            for(CalendarComponent component : schedule.getComponents()){
                VEvent event = (VEvent)component;

                // Match location name with room(s)
                Optional<Property> loc = event.getProperty("LOCATION");
                if(loc.isPresent()) {
                    List<String> locations = new ArrayList<>(List.of(loc.get().getValue().split(", ")));
                    // Intersect locations and room names
                    locations.retainAll(roomNames);
                    for (String location : locations) {
                        // Find room index with matching name
                        int index = roomNames.indexOf(location);

                        // Add
                        if(!bookings.containsKey(partition.get(index)))
                            bookings.put(partition.get(index), new ArrayList<>());
                        bookings.get(partition.get(index)).add(Booking.fromVEvent(event));
                    }
                }
            }
        }

        return bookings;
    }

}
