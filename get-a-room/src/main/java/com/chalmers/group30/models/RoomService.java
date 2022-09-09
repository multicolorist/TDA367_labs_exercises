package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Room;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Facade for finding rooms to the user
 */
public class RoomService {

    // UID for Campus Johanneberg
    private final String areaUID = "a85a8be2-4ff6-4e39-9880-c2adb2a7626f";

    /**
     * Gets currently available rooms
     * @return A list of all rooms
     * @throws IOException If the API request failed for some reason.
     */
    public List<Room> getRooms() throws IOException {
        // Get all buildings and facilities
        JsonObject buildings = ChalmersMapsAPI.informationBoard(areaUID);

        // Get all rooms in buildings and facilities
        List<Room> rooms = new ArrayList<>();
        for(JsonElement e : buildings.get("suggestions").getAsJsonArray()) {
            rooms.add(Room.fromJSON(e.getAsJsonObject()));
        }

        // TODO: Filter rooms based on requirements

        return rooms;
    }
}
