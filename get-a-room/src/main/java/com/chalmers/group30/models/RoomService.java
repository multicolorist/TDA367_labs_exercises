package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.objects.Route;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Facade for finding rooms to the user - the only front-facing interface
 */
public class RoomService {

    // UID for Campus Johanneberg
    // TODO: Use both campuses when basic functionality is there for Johanneberg
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

    /**
     * Gets distance to room from a given location
     * @param uid The room uid
     * @param latitude The latitude of the location
     * @param longitude The longitude of the location
     * @return The distance to the room in meters
     * @throws IOException If an API request failed for some reason.
     */
    public double getDistanceToRoom(String uid, double latitude, double longitude) throws IOException {
        Route route = getRoute(uid, latitude, longitude);
        //TODO: Take decision on which distance to use, currently walking distance
        return route.getDistance();
    }

    /**
     * Gets the route between a room and a given location
     * @param uid The room uid
     * @param latitude The latitude of the location
     * @param longitude The longitude of the location
     * @return A Route object representing the route
     * @throws IOException If an API request failed for some reason.
     */
    public static Route getRoute(String uid, double latitude, double longitude) throws IOException {
        JsonObject roomJson = ChalmersMapsAPI.getInfo(uid);
        Room room = Room.fromJSON(roomJson);
        JsonObject routeJson = ChalmersMapsAPI.route(latitude, longitude, room.getLatitude(), room.getLongitude());
        return Route.fromJSON(routeJson);
    }


}
