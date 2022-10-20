package com.chalmers.getaroom.models;

import com.chalmers.getaroom.models.objects.Location;
import com.chalmers.getaroom.models.objects.Room;
import com.chalmers.getaroom.models.utilities.CacheUpdateProvider;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoomServiceTest {
    @Test
    public void getRooms_shouldReturnProperRooms() throws IOException {
        CacheUpdateProvider<List<Room>> roomCacheUpdateProvider = mock(CacheUpdateProvider.class);

        when(roomCacheUpdateProvider.getNewDataToCache()).thenReturn(Arrays.asList(new Room[]{new Room("Test name", 1, "Test building", "Test floor", "Test streetAddress", "Test time edit id", new UUID(0, 0), new Location(1, 1), new Location(2, 2))}));

        RoomServiceInterface roomServiceInterface = new RoomService(roomCacheUpdateProvider);

        List<Room> rooms = roomServiceInterface.getRooms();

        assertEquals(1, rooms.size());
        assertEquals("Test name", rooms.get(0).name());

    }

    @Test
    public void refreshRoomCache() throws IOException {
        CacheUpdateProvider<List<Room>> roomCacheUpdateProvider = mock(CacheUpdateProvider.class);

        when(roomCacheUpdateProvider.getNewDataToCache()).thenReturn(Arrays.asList(new Room[]{new Room("Test name", 1, "Test building", "Test floor", "Test streetAddress", "Test time edit id", new UUID(0, 0), new Location(1, 1), new Location(2, 2))}));

        RoomServiceInterface roomServiceInterface = new RoomService(roomCacheUpdateProvider);

        roomServiceInterface.refreshRoomCache();

        //Makes sure only the Refresh room cache call can get the test list
        reset(roomCacheUpdateProvider);

        List<Room> rooms = roomServiceInterface.getRooms();

        assertEquals(1, rooms.size());
        assertEquals("Test name", rooms.get(0).name());
    }
}
