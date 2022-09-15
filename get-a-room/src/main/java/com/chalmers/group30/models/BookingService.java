package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Room;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

/**
 * Facade for finding rooms to the user - the only front-facing interface
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BookingService implements BookingServiceInterface{

    private ChalmersMapsAPIInterface chalmersMapsAPI;

    @Autowired
    public BookingService(ChalmersMapsAPIInterface chalmersMapsAPI){
        this.chalmersMapsAPI = chalmersMapsAPI;
    }

    /**
     * Gets bookings for the desired room
     * @param room The room to check bookings for
     * @param startTime The time from which bookings should be checked
     * @param weeksForward How many weeks ahead bookings should be checked. Min 1 Max 10
     * @return A list of bookings for the given time period
     * @throws IOException If the underlying API call fails
     * @throws IllegalArgumentException If weeksForward is too small, or too big (Must be >0 and <=10)
     */
    public List<Booking> getBookings(Room room, Instant startTime, int weeksForward) throws IOException, IllegalArgumentException, ParseException {
        List<Booking> bookings = new ArrayList<>();
        if (weeksForward <= 0 || weeksForward > 10){
            throw new IllegalArgumentException();
        }

        ZonedDateTime zdt = ZonedDateTime.ofInstant(startTime, ZoneId.of("Europe/Paris"));
        Calendar startTimeCal = GregorianCalendar.from(zdt);

        for(int i = 0; i < weeksForward; i++){
            int year = startTimeCal.get(Calendar.YEAR);
            int week = startTimeCal.get(Calendar.WEEK_OF_YEAR);
            JsonArray schedule = chalmersMapsAPI.timeEditSchedule(room.timeEditId(), year, week);
            startTimeCal.add(Calendar.WEEK_OF_YEAR, 1);
            for(JsonElement element : schedule){
                bookings.add(Booking.fromJSON(element.getAsJsonObject()));
            }
        }

        return bookings;
    }
}