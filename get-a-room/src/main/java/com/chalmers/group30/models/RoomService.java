package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.utilities.CacheUpdateProvider;
import com.chalmers.group30.models.utilities.GenericCache;
import com.chalmers.group30.models.utilities.GenericCacheInterface;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * Facade for finding rooms to the user - the only front-facing interface
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoomService implements RoomServiceInterface{

    private GenericCacheInterface<Room> roomCache;

    @Autowired
    public RoomService(CacheUpdateProvider<Room> roomCacheUpdateProvider){
        this.roomCache = new GenericCache<Room>(roomCacheUpdateProvider);
    }

    @Scheduled(cron = "0 0 4 * * *")
    public void RefreshRoomCache() throws IOException{
        roomCache.RefreshCache();
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
