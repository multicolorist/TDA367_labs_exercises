package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Room;

import java.io.IOException;
import java.util.List;

/**
 * Interface defines how a room management service should behave
 */
public interface RoomServiceInterface {

    /**
     * Refreshes the cache
     * @throws IOException If the underlying API call fails
     */
    void refreshRoomCache() throws IOException;

    /**
     * Gets all rooms
     * @return A list of all rooms
     * @throws IOException If the underlying API call fails
     */
    List<Room> getRooms() throws IOException;
}
