package com.chalmers.group30.models.objects;

import com.chalmers.group30.models.ChalmersMapsAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a room in Campus Maps.
 */
public class Room {
    //TODO: Discuss visibility for fields
    List<Booking> bookings;
    String name;
    String building;
    String uid;
    double longitude;
    double latitude;


    /**
     * Initialises a new Room object.
     * @param name The name of the room
     * @param building The building the room is in
     * @param uid The unique identifier of the room
     * @param longitude The longitude of the room
     * @param latitude The latitude of the room
     */
    public Room(String name, String building, String uid, double longitude, double latitude) {
        this.name = name;
        this.building = building;
        this.uid = uid;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Parses a given JSON to a Room object.
     * @param obj A JSON object representing the room
     * @return A Room object from the parsed JSON
     */
    public static Room fromJSON(JsonObject obj) {
        String name = obj.get("title").getAsString();
        String building = obj.get("subtitle").getAsString();
        String uid = obj.get("data").getAsString();
        double longitude = obj.get("longitude").getAsDouble();
        double latitude = obj.get("latitude").getAsDouble();
        return new Room(name, building, uid, longitude, latitude);
    }

    /**
     * Gets all bookings for the current room.
     * @return A list of Bookings for the given room
     * @throws IOException If the API request failed for some reason.
     */
    public List<Booking> getBookings() throws IOException {
        // Get all bookings for room
        // TODO: Remove hardcoded year and week parameters
        JsonArray bookingsJson = ChalmersMapsAPI.timeEditSchedule(this.uid, 2022, 16);

        // Parse all bookings from JSON to objects
        List<Booking> bookings = new ArrayList<>();
        for(JsonElement e : bookingsJson) {
            bookings.add(Booking.fromJSON(e.getAsJsonObject()));
        }

        // Update cached bookings and return newly fetched bookings
        this.bookings = bookings;
        return bookings;
    }

    /**
     * Gets the unique identifier of the room.
     * @return uid The unique identifier of the room
     */
    public String getUid() {
        return uid;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public String getBuilding() {
        return building;
    }
}
