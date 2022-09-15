package com.chalmers.group30.models.objects;

import com.google.gson.JsonObject;

import java.util.UUID;

/**
 * Represents a room in Campus Maps.
 *
 * @param name             The name of the room
 * @param building         The building the room is in
 * @param timeEditId       The ID given to TimeEdit when checking schedule
 * @param uuid             The unique identifier of the room
 * @param location         The rooms location
 * @param entranceLocation The location for the entrance
 * */
public record Room(String name, String building, String timeEditId, UUID uuid, Location location, Location entranceLocation) {
    /**
     * Parses a given JSON to a Room object.
     *
     * @param obj A JSON object representing the room
     * @return A Room object from the parsed JSON
     */
    public static Room fromJSON(JsonObject obj) {
        String name = obj.get("name").getAsString();
        String building = obj.get("building_name").getAsString();
        String timeEditId = obj.get("timeedit_id").getAsString();
        UUID uuid = UUID.fromString(obj.get("id").getAsString());
        double longitude = obj.get("longitude").getAsDouble();
        double latitude = obj.get("latitude").getAsDouble();
        double entranceLongitude = obj.get("entrance_longitude").getAsDouble();
        double entranceLatitude = obj.get("entrance_latitude").getAsDouble();
        return new Room(name, building, timeEditId, uuid, new Location(latitude, longitude), new Location(entranceLatitude, entranceLongitude));
    }
}
