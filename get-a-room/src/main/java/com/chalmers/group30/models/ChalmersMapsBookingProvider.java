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

@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ChalmersMapsBookingProvider implements CacheUpdateProvider<Dictionary<Room, List<Booking>>>{

    private final RoomServiceInterface roomServiceInterface;
    private final ChalmersMapsAPIInterface chalmersMapsAPIInterface;

    private final int weeksForwardToCache = 2;

    @Autowired
    public ChalmersMapsBookingProvider(RoomServiceInterface roomServiceInterface, ChalmersMapsAPIInterface chalmersMapsAPIInterface) {
        this.roomServiceInterface = roomServiceInterface;
        this.chalmersMapsAPIInterface = chalmersMapsAPIInterface;
    }

    public int getWeeksForwardToCache() {
        return weeksForwardToCache;
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
            } catch (ParseException e) {
                e.printStackTrace();
            }catch (IOException e){

            }
        }
        return bookings;
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
}
