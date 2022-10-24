package com.chalmers.getaroom.models.objects;

import com.google.gson.JsonObject;
import net.fortuna.ical4j.model.component.VEvent;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

/**
 * Represents a booking in for a Room in TimeEdit.
 *
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
    public static Booking fromJSON(JsonObject obj) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuuMMdd'T'HHmmss");
        LocalDateTime startTime = LocalDateTime.parse(obj.get("reservation_begin").getAsString(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(obj.get("reservation_end").getAsString(), formatter);
        return new Booking(startTime, endTime);
    }

    /**
     * Parses an iCal VEvent to a Booking object.
     *
     * @param event The VEvent to parse
     * @return A Booking object from the parsed VEvent
     */
    public static Booking fromVEvent(VEvent event) {
        Temporal startTemporal = event.getStartDate().get().getDate();
        LocalDateTime startTime = LocalDateTime.ofInstant(Instant.from(startTemporal), ZoneId.of("Europe/Paris"));

        Temporal endTemporal = event.getEndDate().get().getDate();
        LocalDateTime endTime = LocalDateTime.ofInstant(Instant.from(endTemporal), ZoneId.of("Europe/Paris"));

        return new Booking(startTime, endTime);
    }
}
