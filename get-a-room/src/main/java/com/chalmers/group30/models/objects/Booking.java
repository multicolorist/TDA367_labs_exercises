package com.chalmers.group30.models.objects;

import com.google.gson.JsonObject;

import java.time.Instant;

/**
 * Represents a booking in for a Room in TimeEdit.
 */
public record Booking(Instant startTime, Instant endTime) {
    /**
     * Parses a given JSON to a Booking object.
     *
     * @param obj A JSON object representing the booking
     * @return A Booking object from the parsed JSON
     */
    public static Booking fromJSON(JsonObject obj) {
        Instant startTime = Instant.parse(obj.get("reservation_begin").getAsString());
        Instant endTime = Instant.parse(obj.get("reservation_end").getAsString());
        return new Booking(startTime, endTime);
    }
}
