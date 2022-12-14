package com.chalmers.getaroom.models;

import com.chalmers.getaroom.models.objects.Room;
import com.chalmers.getaroom.models.utilities.CacheUpdateProvider;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * Provides rooms from the API to be cached
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.NO)
class RoomProvider implements CacheUpdateProvider<List<Room>> {

    // UID for Campus Johanneberg and Lindholmen
    private final static UUID[] areaUuids = {UUID.fromString("a85a8be2-4ff6-4e39-9880-c2adb2a7626f"), UUID.fromString("c5d6ba21-7336-49f7-aef7-b161fcc91ad7")};
    private final Logger logger = Logger.getLogger(RoomProvider.class.getName());

    private final List<String> elementTypes = Arrays.asList(
            "breakout_room"
    );

    private final ChalmersMapsAPIInterface chalmersMapsAPI;

    @Autowired
    public RoomProvider(ChalmersMapsAPIInterface chalmersMapsAPI) {
        this.chalmersMapsAPI = chalmersMapsAPI;
    }

    /**
     * Gets all rooms from the API
     *
     * @return A list of all rooms
     * @throws IOException If the underlying API call fails
     */
    @Override
    public List<Room> getNewDataToCache() throws IOException {
        try {
            HashSet<UUID> existingRooms = new HashSet<>();
            // Get all rooms in buildings and facilities
            List<Room> rooms = new ArrayList<>();

            for (UUID areaUuid : areaUuids) {
                // Get all buildings and facilities
                JsonObject buildings = chalmersMapsAPI.informationBoard(areaUuid);

                for (JsonElement building : buildings.get("suggestions").getAsJsonArray()) {
                    JsonObject buildingRooms = chalmersMapsAPI.informationBoard(UUID.fromString(building.getAsJsonObject().get("data").getAsString()));
                    getRoomsInBuilding(existingRooms, rooms, buildingRooms);
                }
            }

            return rooms;
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Failed to get rooms from ChalmersMapsAPI with exception", e);
            throw e;
        }
    }

    private void getRoomsInBuilding(HashSet<UUID> existingRooms, List<Room> rooms, JsonObject buildingRooms) throws IOException {
        for (JsonElement room : buildingRooms.get("suggestions").getAsJsonArray()) {
            // Add room to list if it matches one of the given categories
            if (elementTypes.contains(room.getAsJsonObject().get("element_type").getAsString())) {
                UUID roomUUID = UUID.fromString(room.getAsJsonObject().get("data").getAsString());
                if (existingRooms.contains(roomUUID)) {
                    continue;
                }
                // Get info about specific room from API
                JsonObject roomInfo = chalmersMapsAPI.getInfo(roomUUID);
                JsonObject roomProperties = roomInfo.get("properties").getAsJsonObject();
                if (roomProperties.has("timeedit_id")) {
                    String timeeditId = roomProperties.get("timeedit_id").getAsString();
                    JsonObject timeEditInfo = null;
                    try {
                        timeEditInfo = chalmersMapsAPI.getTimeEditInfo(timeeditId);
                    } catch (Exception e) {
                        // Failed to get timeedit info
                        logger.info("Failed to get timeedit info for room with UUID of" + roomUUID + " and time edit id of " + timeeditId + "and will as a result not have seat information");
                    }

                    rooms.add(Room.fromJSON(roomProperties, timeEditInfo));
                    existingRooms.add(roomUUID);
                }
            }
        }
    }
}
