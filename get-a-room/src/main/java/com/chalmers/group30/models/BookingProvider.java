package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.utilities.CacheUpdateProvider;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.text.ParseException;
import java.time.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BookingProvider implements CacheUpdateProvider<Dictionary<Room, List<Booking>>> , BookingProviderInterface{

    private final RoomServiceInterface roomServiceInterface;
    private final ChalmersMapsAPIInterface chalmersMapsAPIInterface;
    private final Logger logger = Logger.getLogger(BookingProvider.class.getName());
    private final int weeksForwardToCache = 2;

    @Autowired
    public BookingProvider(RoomServiceInterface roomServiceInterface, ChalmersMapsAPIInterface chalmersMapsAPIInterface) {
        this.roomServiceInterface = roomServiceInterface;
        this.chalmersMapsAPIInterface = chalmersMapsAPIInterface;
    }

    public int getWeeksForwardToCache() {
        return weeksForwardToCache;
    }

    /**
     * Gets bookings for the desired room for the next x weeks based on weeksForwardToCache
     * @param room The room to check bookings for
     * @param startTime The time from which bookings should be checked
     * @return A list of bookings for the given time period
     * @throws IOException If the underlying API call fails
     * @throws ParseException If the underlying API call returns invalid data
     * @throws IllegalArgumentException If the room is null
     */
    public List<Booking> getBookings(Room room, LocalDateTime startTime) throws IOException, IllegalArgumentException, ParseException {
        List<Booking> bookings = new ArrayList<>();
        if (room == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        if (weeksForwardToCache <= 0){
            throw new IllegalArgumentException(weeksForwardToCache + " is not a valid number of weeks to cache");
        }

        ZonedDateTime zdt = ZonedDateTime.ofLocal(startTime, ZoneId.of("Europe/Paris"), ZoneOffset.ofHours(1));
        Calendar startTimeCal = GregorianCalendar.from(zdt);

        for(int i = 0; i < weeksForwardToCache; i++){
            int year = startTimeCal.get(Calendar.YEAR);
            int week = startTimeCal.get(Calendar.WEEK_OF_YEAR);
            JsonArray schedule = chalmersMapsAPIInterface.timeEditSchedule(room.timeEditId(), year, week);
            startTimeCal.add(Calendar.WEEK_OF_YEAR, 1);
            for(JsonElement element : schedule){
                bookings.add(Booking.fromJSON(element.getAsJsonObject()));
            }
        }

        return bookings;
    }

    /**
     * Gets all bookings for the next x weeks based on weeksForwardToCache
     * @return A dictionary with rooms as keys and a list of bookings as values
     * @throws IOException If the underlying API call fails
     */
    @Override
        public Dictionary<Room, List<Booking>> getNewDataToCache() throws IOException {
        Dictionary<Room, List<Booking>> bookings = new Hashtable<>();

        for (Room room : roomServiceInterface.getRooms()) {
            try {
                bookings.put(room, getBookings(room, LocalDateTime.now(ZoneId.of("Europe/Paris"))));
            } catch (Exception e) {
                logger.log(Level.WARNING, "Failed to get bookings for room " + room.name() + " with UUID " + room.uuid() + " and time edit ID " + room.timeEditId() + ". Skipping this room.", e);
            }
        }
        return bookings;
    }
}
