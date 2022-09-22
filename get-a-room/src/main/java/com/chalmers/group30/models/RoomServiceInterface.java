package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Room;

import java.io.IOException;
import java.util.List;

public interface RoomServiceInterface {
    void refreshRoomCache() throws IOException;
    List<Room> getRooms() throws IOException;
}
