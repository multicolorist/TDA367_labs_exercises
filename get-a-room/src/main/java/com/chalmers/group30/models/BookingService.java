package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.utilities.CacheUpdateProvider;
import com.chalmers.group30.models.utilities.GenericCache;
import com.chalmers.group30.models.utilities.GenericCacheInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * Facade for finding rooms to the user - the only front-facing interface
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BookingService implements BookingServiceInterface{

    private GenericCacheInterface<Dictionary<Room, List<Booking>>> bookingCache;
    private BookingProviderInterface bookingProvider;

    @Autowired
    public BookingService(CacheUpdateProvider<Dictionary<Room, List<Booking>>> bookingCacheUpdateProvider, BookingProviderInterface bookingProvider){
        this.bookingCache = new GenericCache<Dictionary<Room, List<Booking>>>(bookingCacheUpdateProvider);
        this.bookingProvider = bookingProvider;
    }

    /**
     * Refreshes the cache
     * @throws IOException If the underlying API call fails
     */
    public void refreshBookingCache() throws IOException{
        bookingCache.refreshCache();
    }

    /**
     * Gets bookings for the desired room for the next 2 weeks
     * @param room The room to get bookings for
     * @return A list of bookings for the room
     * @throws IOException If the underlying API call fails
     */
    public List<Booking> getBookings(Room room) throws IOException, ParseException {

        List<Booking> bookings = bookingCache.getData().get(room);

        if (bookings == null) {
            bookings = bookingProvider.getBookings(room, LocalDateTime.now(ZoneId.of("Europe/Paris")), 2);
        }

        return bookings;
    }
}
