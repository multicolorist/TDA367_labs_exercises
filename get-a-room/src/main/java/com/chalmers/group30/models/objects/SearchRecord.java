package com.chalmers.group30.models.objects;

import java.util.List;

/**
 * Represents a result for a room given by the API
 *
 * @param room          The room represented by the result
 * @param bookings      The bookings belonging to the room
 * @param birdsDistance The birds distance to the room from the user
 */
public record SearchRecord(Room room, List<Booking> bookings, double birdsDistance) {
}
