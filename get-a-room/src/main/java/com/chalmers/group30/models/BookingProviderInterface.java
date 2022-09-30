package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Room;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingProviderInterface {
    /**
     * Gets bookings for the desired room
     * @param room The room to check bookings for
     * @param startTime The time from which bookings should be checked
     * @param weeksForward How many weeks ahead bookings should be checked. Min 1 Max 10
     * @return A list of bookings for the given time period
     * @throws IOException If the underlying API call fails
     * @throws IllegalArgumentException If weeksForward is too small, or too big (Must be >0 and <=10)
     */
    List<Booking> getBookings(Room room, LocalDateTime startTime, int weeksForward) throws IOException, IllegalArgumentException, ParseException;
}
