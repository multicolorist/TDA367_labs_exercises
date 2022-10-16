package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.utilities.CacheUpdateProvider;
import com.chalmers.group30.models.utilities.GenericCache;
import com.chalmers.group30.models.utilities.GenericCacheInterface;
import net.fortuna.ical4j.data.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.logging.Logger;

/**
 * Manages the cache of bookings
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BookingService implements BookingServiceInterface{

    private GenericCacheInterface<Dictionary<Room, List<Booking>>> bookingCache;
    private final Logger logger = Logger.getLogger(BookingService.class.getName());

    @Autowired
    public BookingService(CacheUpdateProvider<Dictionary<Room, List<Booking>>> bookingCacheUpdateProvider){
        this.bookingCache = new GenericCache<Dictionary<Room, List<Booking>>>(bookingCacheUpdateProvider);
        try{
            refreshBookingCache();
        }catch (Exception e){
            logger.log(java.util.logging.Level.SEVERE, "Failed to do initial booking cache retrieval", e);
        }
    }

    /**
     * Refreshes the booking cache. Called automatically every 15 minutes
     * @throws IOException If the underlying API call fails
     */
    @Scheduled(cron = "0 */15 * * * *")
    public void refreshBookingCache() throws IOException{
        logger.info("Refreshing booking cache...");
        try {
            bookingCache.refreshCache();
            logger.info("Booking cache refreshed");
        }catch (IOException e){
            logger.log(java.util.logging.Level.SEVERE, "Failed to refresh booking cache", e);
            throw e;
        }

    }

    /**
     * Gets bookings for the desired room for the next x weeks the based on the provided booking provider
     * @param room The room to get bookings for
     * @return A list of bookings for the room
     * @throws IOException If the underlying API call fails
     */
    public List<Booking> getBookings(Room room) throws IOException {

        List<Booking> bookings = bookingCache.getData().get(room);

        if (bookings == null) {
            logger.warning("Room " + room.name() + " UUID " + room.uuid() + " was not found in booking cache.");
        }

        return bookings;
    }
}
