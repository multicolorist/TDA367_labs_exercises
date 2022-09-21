package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.utilities.CacheUpdateProvider;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RoomProvider implements CacheUpdateProvider<Room> {

    // UID for Campus Johanneberg
    // TODO: Use both campuses when basic functionality is there for Johanneberg
    private final static UUID areaUuid = UUID.fromString("a85a8be2-4ff6-4e39-9880-c2adb2a7626f");

    private final List<String> elementTypes = Arrays.asList(
            "breakout_room",
            "reading_room'",
            "quiet_reading_room",
            "computer_room",
            "meeting_room",
            "conference_room",
            "multifunctional_room",
            "practice_room",
            "conversation_room"
    );

    private ChalmersMapsAPIInterface chalmersMapsAPI;

    @Autowired
    public RoomProvider(ChalmersMapsAPIInterface chalmersMapsAPI){
        this.chalmersMapsAPI = chalmersMapsAPI;
    }

    @Override
    public List<Room> getNewDataToCache() throws IOException {
        // Get all buildings and facilities
        JsonObject buildings = chalmersMapsAPI.informationBoard(areaUuid);

        // Get all rooms in buildings and facilities
        List<Room> rooms = new ArrayList<>();
        for(JsonElement building : buildings.get("suggestions").getAsJsonArray()) {
            JsonObject buildingRooms = chalmersMapsAPI.informationBoard(UUID.fromString(building.getAsJsonObject().get("data").getAsString()));
            for(JsonElement room : buildingRooms.get("suggestions").getAsJsonArray()){
                // Add room to list if it matches one of the given categories
                if(elementTypes.contains(room.getAsJsonObject().get("element_type").getAsString())){
                    // Get info about specific room from API
                    JsonObject roomInfo = chalmersMapsAPI.getInfo(UUID.fromString(room.getAsJsonObject().get("data").getAsString()));
                    JsonObject roomProperties = roomInfo.get("properties").getAsJsonObject();
                    if (roomProperties.has("timeedit_id")){
                        rooms.add(Room.fromJSON(roomProperties));
                    }

                }
            }
        }
        return rooms;
    }
}
