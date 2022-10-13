package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Room;
import net.fortuna.ical4j.data.ParserException;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * Interface defines how a booking management service should behave
 */
public interface BookingServiceInterface {
    /**
     * Gets bookings for the desired room for the next 2 weeks
     * @param room The room to get bookings for
     * @return A list of bookings for the room
     * @throws IOException If the underlying API call fails
     */
    List<Booking> getBookings(Room room) throws IOException;

    /**
     * Refreshes the cache
     * @throws IOException If the underlying API call fails
     */
    void refreshBookingCache() throws IOException;
}
