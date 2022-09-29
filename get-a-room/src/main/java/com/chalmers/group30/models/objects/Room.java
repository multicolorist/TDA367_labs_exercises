package com.chalmers.group30.models.objects;

import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.UUID;

/**
 * Represents a room in Campus Maps.
 *
 * @param name             The name of the room
 * @param seats            The number of seats in the room. -1 if unknown
 * @param building         The building the room is in
 * @param floor            The floor the room is on
 * @param streetAddress    The address of the building the room is in
 * @param timeEditId       The ID given to TimeEdit when checking schedule
 * @param uuid             The unique identifier of the room
 * @param location         The rooms location
 * @param entranceLocation The location for the entrance
 * */
public record Room(String name, int seats, String building, String floor, String streetAddress, String timeEditId, UUID uuid, Location location, Location entranceLocation) implements Serializable {
    /**
     * Parses a given JSON to a Room object.
     *
     * @param infoObj A JSON object representing the room
     * @return A Room object from the parsed JSON
     */
    public static Room fromJSON(JsonObject infoObj, JsonObject timeeditInfoObj) {
        String name = infoObj.get("name").getAsString();
        String building = infoObj.get("building_name").getAsString();
        String floor;
        String streetAddress;
        if (infoObj.has("floor_level")) {
            floor = infoObj.get("floor_level").getAsString();
        } else {
            floor = "";
        }
        if (infoObj.has("street_address")) {
            streetAddress = infoObj.get("street_address").getAsString();
        } else {
            streetAddress = "";
        }
        String timeEditId = infoObj.get("timeedit_id").getAsString();
        UUID uuid = UUID.fromString(infoObj.get("id").getAsString());
        double longitude = infoObj.get("longitude").getAsDouble();
        double latitude = infoObj.get("latitude").getAsDouble();
        double entranceLongitude;
        double entranceLatitude;
        if(infoObj.has("entrance_longitude") && infoObj.has("entrance_latitude")){
            entranceLongitude = infoObj.get("entrance_longitude").getAsDouble();
            entranceLatitude = infoObj.get("entrance_latitude").getAsDouble();
        }else {
            //Fallback to "normal" location
            entranceLongitude = longitude;
            entranceLatitude = latitude;
        }

        int seats;

        try {
            seats = timeeditInfoObj.get("seats").getAsInt();
        }catch (Exception e){
            seats = -1;
        }



        return new Room(name, seats, building, floor, streetAddress, timeEditId, uuid, new Location(latitude, longitude), new Location(entranceLatitude, entranceLongitude));
    }
}
