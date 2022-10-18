package com.chalmers.group30.models.objects;

import java.time.LocalDateTime;

/**
 * Parameters of a room search
 *
 * @param userLocation The user location, can be null
 * @param groupSize    The group size. Must be at least 1
 * @param startTime    The desired start time
 * @param endTime      The desired end time
 */
public record SearchQuery(Location userLocation, int groupSize, LocalDateTime startTime, LocalDateTime endTime) {
}

