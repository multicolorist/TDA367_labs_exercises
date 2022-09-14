package com.chalmers.group30.models.objects;

import java.time.Instant;
import java.util.List;

public record SearchResult(List<SearchRecord> searchRecords) {
    public List<SearchRecord> getFilteredResults(Location userLocation, int groupSize, Instant startTime) {
        return null;
    }
}
