package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Room;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.util.List;

public interface BookingServiceInterface {
    List<Booking> getBookings(Room room, Instant startTime, int weeksForward) throws IOException, IllegalArgumentException, ParseException;
}
