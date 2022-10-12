package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.utilities.CacheUpdateProvider;
import net.fortuna.ical4j.data.ParserException;
import net.fortuna.ical4j.model.component.CalendarComponent;
import net.fortuna.ical4j.model.component.VEvent;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

public class TimeEditBookingProvider implements CacheUpdateProvider<Dictionary<Room, List<Booking>>> {

    private final RoomServiceInterface roomServiceInterface;
    private final TimeEditAPIInterface timeEditAPIInterface;

    private final int weeksForwardToCache = 2;

    @Autowired
    public TimeEditBookingProvider(RoomServiceInterface roomServiceInterface, TimeEditAPIInterface timeEditAPIInterface) {
        this.roomServiceInterface = roomServiceInterface;
        this.timeEditAPIInterface = timeEditAPIInterface;
    }

    @Override
    public Dictionary<Room, List<Booking>> getNewDataToCache() throws IOException {
        Dictionary<Room, List<Booking>> bookings = new Hashtable<>();

        for (Room room : roomServiceInterface.getRooms()) {
            try {
                bookings.put(room, getBookings(room, LocalDateTime.now(ZoneId.of("Europe/Paris"))));
            } catch (ParseException | ParserException e) {
                e.printStackTrace();
            }catch (IOException e){

            }
        }
        return bookings;
    }

    private List<Booking> getBookings(Room room, LocalDateTime startTime) throws IOException, IllegalArgumentException, ParseException, ParserException {
        List<Booking> bookings = new ArrayList<>();
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (weeksForwardToCache <= 0){
            throw new IllegalArgumentException(weeksForwardToCache + " is not a valid number of weeks to cache");
        }

        LocalDateTime endTime = startTime.plusWeeks(weeksForwardToCache);
        net.fortuna.ical4j.model.Calendar schedule = timeEditAPIInterface.getSchedule(room.timeEditId(), startTime, endTime);

        for(CalendarComponent component : schedule.getComponents()){
            bookings.add(Booking.fromVEvent((VEvent)component));
        }


        return bookings;
    }
}
