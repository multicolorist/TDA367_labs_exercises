package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.utilities.CacheUpdateProvider;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Provides bookings from the API to be cached
 */
@Deprecated
public class ChalmersMapsBookingProvider implements CacheUpdateProvider<Dictionary<Room, List<Booking>>>{

    private final RoomServiceInterface roomServiceInterface;
    private final ChalmersMapsAPIInterface chalmersMapsAPIInterface;
    private final Logger logger = Logger.getLogger(ChalmersMapsBookingProvider.class.getName());
    private final int weeksForwardToCache = 2;
    
    public ChalmersMapsBookingProvider(RoomServiceInterface roomServiceInterface, ChalmersMapsAPIInterface chalmersMapsAPIInterface) {
        this.roomServiceInterface = roomServiceInterface;
        this.chalmersMapsAPIInterface = chalmersMapsAPIInterface;
    }

    private List<Booking> getBookings(Room room, LocalDateTime startTime) throws IOException, IllegalArgumentException, ParseException {
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
