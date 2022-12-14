package com.chalmers.getaroom.models;

import com.chalmers.getaroom.models.objects.Location;
import com.chalmers.getaroom.models.objects.Route;
import com.chalmers.getaroom.models.objects.SearchQuery;
import com.chalmers.getaroom.models.objects.SearchResult;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

/**
 * Facade for the GetARoom model. This class is the only one that should be used outside the model package.
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.NO)
class GetARoomFacade implements GetARoomFacadeInterface {

    private final RouteServiceInterface routeService;
    private final SearchServiceInterface searchService;
    private final ChalmersMapsAPIInterface chalmersMapsAPI;

    @Autowired
    public GetARoomFacade(RouteServiceInterface routeService, SearchServiceInterface searchService, ChalmersMapsAPIInterface chalmersMapsAPI) {
        this.routeService = routeService;
        this.searchService = searchService;
        this.chalmersMapsAPI = chalmersMapsAPI;
    }

    /**
     * Search for matching rooms that are free at the given time
     *
     * @param searchQuery The search query
     * @return A list of matching rooms with a distance from the user and all existing bookings for the room
     * @throws IOException              If the API request failed for some reason.
     * @throws IllegalArgumentException If ether the group size is less than 1 or the start time is after the end time
     */
    @Override
    public SearchResult search(SearchQuery searchQuery) throws IOException, IllegalArgumentException {
        return searchService.search(searchQuery);
    }

    /**
     * Get the walking route between two locations
     *
     * @param userLocation        The user location
     * @param destinationLocation The destination location
     * @return The walking route
     * @throws IOException If the API request failed for some reason.
     */
    @Override
    public Route getWalkingRoute(Location userLocation, Location destinationLocation) throws IOException {
        return routeService.getRoute(userLocation, destinationLocation);
    }

    /**
     * Get GeoJson object for POIs
     *
     * @return A GeoJson object representing different Points of Interest
     * @throws IOException If the underlying API request failed for some reason
     */
    @Override
    public JsonObject getGeoJsonPOI() throws IOException {
        return chalmersMapsAPI.getGeoJsonPOI();
    }

    /**
     * Get GeoJson object for relevant buildings
     *
     * @return A GeoJson object representing different buildings
     * @throws IOException If the underlying API request failed for some reason
     */
    @Override
    public JsonObject getGeoJsonBuildings() throws IOException {
        return chalmersMapsAPI.getGeoJsonBuildings();
    }
}

