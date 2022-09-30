package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.*;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface GetARoomFacadeInterface {
    SearchResult search(SearchQuery searchQuery) throws IOException, IllegalArgumentException;
    Route getWalkingRoute(Location userLocation, Location destinationLocation) throws IOException;
}
