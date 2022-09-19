package com.chalmers.group30.models.objects;

import java.time.Instant;
import java.util.List;

/**
 * Represents a complete result given a search.
 * @param searchRecords The collection of all items from the search.
 */
public record SearchResult(List<SearchRecord> searchRecords) {
    public List<SearchRecord> getFilteredResults(Location userLocation, int groupSize, Instant startTime) {
        return null;
    }
}
