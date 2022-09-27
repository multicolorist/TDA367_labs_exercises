package com.chalmers.group30.models.objects;

import java.time.Instant;

public record SearchQuery(Location userLocation, int groupSize, Instant startTime, Instant endTime) {
}
