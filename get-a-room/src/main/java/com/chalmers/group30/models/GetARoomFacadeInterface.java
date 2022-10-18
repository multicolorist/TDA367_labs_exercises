package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.*;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Interface for the facade of the GetARoom model -- the only front-facing interface
 */
public interface GetARoomFacadeInterface {
    /**
     * Search for matching rooms that are free at the given time
     * @param searchQuery The search query
     * @return A list of matching rooms with a distance from the user and all existing bookings for the room
     * @throws IOException If the API request failed for some reason.
     * @throws IllegalArgumentException If ether the group size is less than 1 or the start time is after the end time
     */
    SearchResult search(SearchQuery searchQuery) throws IOException, IllegalArgumentException;
    /**
     * Get the walking route between two locations
     * @param userLocation The user location
     * @param destinationLocation The destination location
     * @return The walking route
     * @throws IOException If the API request failed for some reason.
     */
    Route getWalkingRoute(Location userLocation, Location destinationLocation) throws IOException;

    /**
     * Get GeoJson object for POIs
     * @return A GeoJson object representing different Points of Interest
     * @throws IOException If the underlying API request failed for some reason
     */
    JsonObject geoJsonPOI() throws IOException;

    /**
     * Get GeoJson object for relevant buildings
     * @return A GeoJson object representing different buildings
     * @throws IOException If the underlying API request failed for some reason
     */
    JsonObject geoJsonBuildings() throws IOException;
}
