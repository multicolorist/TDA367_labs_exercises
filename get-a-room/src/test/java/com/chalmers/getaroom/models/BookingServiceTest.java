package com.chalmers.getaroom.models;

import com.chalmers.getaroom.models.objects.Booking;
import com.chalmers.getaroom.models.objects.Location;
import com.chalmers.getaroom.models.objects.Room;
import com.chalmers.getaroom.models.utilities.CacheUpdateProvider;
import net.fortuna.ical4j.data.ParserException;
import org.junit.Test;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookingServiceTest {
    @Test
    public void getBookings_roomIsInCache() throws IOException, ParseException, ParserException {
        Room mockRoom = new Room("Chalmers Test", 1, "Building A", "Floor 1", "Adress A", "chalmers:test", UUID.randomUUID(), new Location(0, 0), new Location(1, 1));
        CacheUpdateProvider<Dictionary<Room, List<Booking>>> cacheUpdateProvider = mock(CacheUpdateProvider.class);

        Dictionary<Room, List<Booking>> bookingDict = new Hashtable<>();

        bookingDict.put(mockRoom, Arrays.asList(new Booking[]{new Booking(LocalDateTime.MIN, LocalDateTime.MAX)}));

        when(cacheUpdateProvider.getNewDataToCache()).thenReturn(bookingDict);

        BookingServiceInterface bookingService = new BookingService(cacheUpdateProvider);

        bookingService.refreshBookingCache();

        List<Booking> bookings = bookingService.getBookings(mockRoom);

        assertEquals(1, bookings.size());

    }

}
