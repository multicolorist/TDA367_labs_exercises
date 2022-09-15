package com.chalmers.group30.models.objects;

import com.google.gson.JsonObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

/**
 * Represents a booking in for a Room in TimeEdit.
 * @param startTime The time at which the booking starts
 * @param endTime   The time at which the booking ends
 */
public record Booking(LocalDateTime startTime, LocalDateTime endTime) {
    /**
     * Parses a given JSON to a Booking object.
     *
     * @param obj A JSON object representing the booking
     * @return A Booking object from the parsed JSON
     */
    public static Booking fromJSON(JsonObject obj) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuuMMdd'T'HHmmss");
        LocalDateTime startTime = LocalDateTime.parse(obj.get("reservation_begin").getAsString(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(obj.get("reservation_end").getAsString(), formatter);
        return new Booking(startTime, endTime);
    }
}
