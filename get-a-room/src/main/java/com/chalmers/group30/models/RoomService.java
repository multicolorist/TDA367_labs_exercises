package com.chalmers.group30.models;

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
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Logger;

/**
 * Facade for finding rooms to the user - the only front-facing interface
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoomService implements RoomServiceInterface{

    private GenericCacheInterface<List<Room>> roomCache;
    private final Logger logger = Logger.getLogger(RoomService.class.getName());

    @Autowired
    public RoomService(CacheUpdateProvider<List<Room>> roomCacheUpdateProvider){
        this.roomCache = new GenericCache<List<Room>>(roomCacheUpdateProvider);
        try {
            //refreshRoomCache();
        }catch (Exception e){

        }
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void refreshRoomCache() throws IOException{
        logger.info("Refreshing room cache...");
        try {
            roomCache.refreshCache();
            logger.info("Room cache refreshed");
        }catch (IOException e){
            logger.log(java.util.logging.Level.SEVERE, "Failed to refresh room cache", e);
            throw e;
        }
    }

    /**
     * Gets currently available rooms
     * @return A list of all rooms
     * @throws IOException If the API request failed for some reason.
     */
    public List<Room> getRooms() throws IOException {
        return roomCache.getData();
    }

}
