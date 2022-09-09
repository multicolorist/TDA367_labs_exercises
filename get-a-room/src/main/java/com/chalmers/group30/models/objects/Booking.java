package com.chalmers.group30.models.objects;

import com.google.gson.JsonObject;

import java.time.Instant;

/**
 * Represents a booking in for a Room in TimeEdit.
 */
public class Booking {
    Instant start;
    Instant end;

    /**
     * Initialises a new Booking object.
     * @param startTime The time when the booking starts.
     * @param endTime The time when the booking ends.
     */
    public Booking(Instant startTime, Instant endTime) {
        this.start = startTime;
        this.end = endTime;
    }

    /**
     * Parses a given JSON to a Booking object.
     * @param obj A JSON object representing the booking
     * @return A Booking object from the parsed JSON
     */
    public static Booking fromJSON(JsonObject obj) {
        Instant startTime = Instant.parse(obj.get("reservation_begin").getAsString());
        Instant endTime = Instant.parse(obj.get("reservation_end").getAsString());
        return new Booking(startTime, endTime);
    }
}
