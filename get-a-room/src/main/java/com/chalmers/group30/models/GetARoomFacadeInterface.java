package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Route;
import com.chalmers.group30.models.objects.SearchRecord;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

public interface GetARoomFacadeInterface {
    List<SearchRecord> search(Location userLocation, int groupSize, LocalDateTime startTime, LocalDateTime endTime) throws IOException;
    Route getWalkingRoute(Location userLocation, Location destinationLocation) throws IOException;
}
