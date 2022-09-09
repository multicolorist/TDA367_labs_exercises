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
    List<Booking> bookings;
    String name;
    String building;
    String uid;

    /**
     * Initialises a new Room object.
     * @param name The name of the room
     * @param building The building the room is in
     * @param uid The unique identifier of the room
     */
    public Room(String name, String building, String uid) {
        this.name = name;
        this.building = building;
        this.uid = uid;
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
        return new Room(name, building, uid);
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
}
