package com.chalmers.group30.models.objects;

import java.util.List;

public record SearchRecord(Room room, List<Booking> bookings, double birdsDistance) {
}
