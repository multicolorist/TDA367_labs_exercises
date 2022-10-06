package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GetARoomFacade implements GetARoomFacadeInterface {


    private final RouteServiceInterface routeService;
    private final SearchServiceInterface searchService;

    @Autowired
    public GetARoomFacade(RouteServiceInterface routeService, SearchServiceInterface searchService) {

        this.routeService = routeService;
        this.searchService = searchService;
    }

    /**
     * Search for matching rooms that are free at the given time
     * @param searchQuery The search query
     * @return A list of matching rooms with a distance from the user and all existing bookings for the room
     * @throws IOException If the API request failed for some reason.
     * @throws IllegalArgumentException If ether the group size is less than 1 or the start time is after the end time
     */
    @Override
    public SearchResult search(SearchQuery searchQuery) throws IOException, IllegalArgumentException {
        return searchService.search(searchQuery);
    }

    /**
     * Get the walking route between two locations
     * @param userLocation The user location
     * @param destinationLocation The destination location
     * @return The walking route
     * @throws IOException If the API request failed for some reason.
     */
    @Override
    public Route getWalkingRoute(Location userLocation, Location destinationLocation) throws IOException {
        return routeService.getRoute(userLocation, destinationLocation);
    }
}

