package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.utilities.CacheUpdateProvider;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

public class RoomServiceTest {
    @Test
    public void getRooms_shouldReturnProperRooms() throws IOException {
        CacheUpdateProvider<Room> roomCacheUpdateProvider = mock(CacheUpdateProvider.class);

        when(roomCacheUpdateProvider.getNewDataToCache()).thenReturn(Arrays.asList(new Room[]{new Room("Test name", "Test building", "Test floor", "Test streetAddress", "Test time edit id", new UUID(0,0),new Location(1,1), new Location(2,2))}));

        RoomServiceInterface roomServiceInterface = new RoomService(roomCacheUpdateProvider);

        List<Room> rooms = roomServiceInterface.getRooms();

        assertEquals(1, rooms.size());
        assertEquals("Test name", rooms.get(0).name());

    }

    @Test
    public void refreshRoomCache() throws IOException {
        CacheUpdateProvider<Room> roomCacheUpdateProvider = mock(CacheUpdateProvider.class);

        when(roomCacheUpdateProvider.getNewDataToCache()).thenReturn(Arrays.asList(new Room[]{new Room("Test name", "Test building", "Test floor", "Test streetAddress", "Test time edit id", new UUID(0,0),new Location(1,1), new Location(2,2))}));

        RoomServiceInterface roomServiceInterface = new RoomService(roomCacheUpdateProvider);

        roomServiceInterface.RefreshRoomCache();

        //Makes sure only the Refresh room cache call can get the test list
        reset(roomCacheUpdateProvider);

        List<Room> rooms = roomServiceInterface.getRooms();

        assertEquals(1, rooms.size());
        assertEquals("Test name", rooms.get(0).name());
    }
}
