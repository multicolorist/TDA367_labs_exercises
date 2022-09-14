package com.chalmers.group30.models.objects;

import com.google.gson.JsonObject;

import java.util.UUID;

/**
 * Represents a room in Campus Maps.
 *
 * @param name     The name of the room
 * @param building The building the room is in
 * @param uuid     The unique identifier of the room
 * @param location The rooms location
 * */
public record Room(String name, String building, UUID uuid, Location location) {
    /**
     * Parses a given JSON to a Room object.
     *
     * @param obj A JSON object representing the room
     * @return A Room object from the parsed JSON
     */
    public static Room fromJSON(JsonObject obj) {
        String name = obj.get("title").getAsString();
        String building = obj.get("subtitle").getAsString();
        UUID uuid = UUID.fromString(obj.get("data").getAsString());
        double longitude = obj.get("longitude").getAsDouble();
        double latitude = obj.get("latitude").getAsDouble();
        return new Room(name, building, uuid, new Location(longitude, latitude));
    }
}
