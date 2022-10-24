package com.chalmers.getaroom.models;

import com.chalmers.getaroom.models.objects.Room;
import com.chalmers.getaroom.models.utilities.CacheUpdateProvider;
import com.chalmers.getaroom.models.utilities.GenericCache;
import com.chalmers.getaroom.models.utilities.GenericCacheInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Service for managing the cache of rooms
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.NO)
class RoomService implements RoomServiceInterface {

    private final GenericCacheInterface<List<Room>> roomCache;
    private final Logger logger = Logger.getLogger(RoomService.class.getName());

    @Autowired
    public RoomService(CacheUpdateProvider<List<Room>> roomCacheUpdateProvider) {
        this.roomCache = new GenericCache<List<Room>>(roomCacheUpdateProvider);
        try {
            refreshRoomCache();
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to do initial room cache retrieval", e);
        }
    }

    /**
     * Refreshes the cache of rooms from the API. Automatically called 04:00 every day
     *
     * @throws IOException If the underlying API call fails
     */
    @Scheduled(cron = "0 0 4 * * *")
    public void refreshRoomCache() throws IOException {
        logger.info("Refreshing room cache...");
        try {
            roomCache.refreshCache();
            logger.info("Room cache refreshed");
        } catch (IOException e) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to refresh room cache", e);
            throw e;
        }
    }

    /**
     * Gets currently available rooms
     *
     * @return A list of all rooms
     * @throws IOException If the API request failed for some reason.
     */
    public List<Room> getRooms() throws IOException {
        return roomCache.getData();
    }

}
