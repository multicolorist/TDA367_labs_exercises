package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Room;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingProviderInterface {
    /**
     * Gets bookings for the desired room
     * @param room The room to check bookings for
     * @param startTime The time from which bookings should be checked
     * @return A list of bookings for the given time period
     * @throws IOException If the underlying API call fails
     * @throws IllegalArgumentException If room is null
     */
    List<Booking> getBookings(Room room, LocalDateTime startTime) throws IOException, IllegalArgumentException, ParseException;
}
