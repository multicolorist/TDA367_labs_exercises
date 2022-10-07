package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.SearchQuery;
import com.chalmers.group30.models.objects.SearchResult;

import java.io.IOException;

public interface SearchServiceInterface {
    /**
     * Search for matching rooms that are free at the given time
     * @param searchQuery The search query
     * @return A list of matching rooms with a distance from the user and all existing bookings for the room
     * @throws IOException If the API request failed for some reason.
     * @throws IllegalArgumentException If ether the group size is less than 1 or the start time is after the end time
     */
    SearchResult search(SearchQuery searchQuery) throws IOException, IllegalArgumentException;
}
