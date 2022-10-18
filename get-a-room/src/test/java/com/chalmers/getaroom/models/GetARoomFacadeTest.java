package com.chalmers.getaroom.models;

import com.chalmers.getaroom.models.objects.*;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GetARoomFacadeTest {

    @Test
    void search_shouldReturnProperResults() throws IOException {
        SearchServiceInterface searchServiceInterface = mock(SearchServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);
        ChalmersMapsAPIInterface chalmersMapsAPIInterface = mock(ChalmersMapsAPIInterface.class);

        SearchQuery query = new SearchQuery(new Location(1, 1), 2, LocalDateTime.of(2022, 1, 1, 12, 15), LocalDateTime.of(2022, 1, 1, 13, 0));

        SearchResult expectedSearchResult = new SearchResult(query, List.of(
                new SearchRecord(
                        new Room("Test 1", 2, "Building", "Floor 2", "Address",
                                "chalmers:test1", UUID.fromString("00000000-0000-0000-0000-000000000001"),
                                new Location(1, 1),
                                new Location(1, 1)),
                        List.of(new Booking(LocalDateTime.of(2022, 1, 1, 12, 0),
                                LocalDateTime.of(2022, 1, 1, 13, 0))),
                        1000)));

        when(searchServiceInterface.search(query)).thenReturn(expectedSearchResult);

        GetARoomFacade getARoomFacade = new GetARoomFacade(routeServiceInterface, searchServiceInterface, chalmersMapsAPIInterface);

        SearchResult searchResult = getARoomFacade.search(query);

        assertEquals(expectedSearchResult, searchResult);
    }

    @Test
    void getWalkingRoute_shouldReturnProperResults() throws IOException {
        SearchServiceInterface searchServiceInterface = mock(SearchServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);
        ChalmersMapsAPIInterface chalmersMapsAPIInterface = mock(ChalmersMapsAPIInterface.class);

        Location userLocation = new Location(1, 1);
        Location destinationLocation = new Location(2, 2);

        Route route = new Route(1000.0, Arrays.asList(new Location(1, 1), new Location(2, 2)));

        when(routeServiceInterface.getRoute(userLocation, destinationLocation)).thenReturn(route);

        GetARoomFacade getARoomFacade = new GetARoomFacade(routeServiceInterface, searchServiceInterface, chalmersMapsAPIInterface);

        Route result = getARoomFacade.getWalkingRoute(userLocation, destinationLocation);

        assertEquals(route, result);
    }

    @Test
    void getGeoJsonPOI_shouldReturnProperResults() throws IOException {
        SearchServiceInterface searchServiceInterface = mock(SearchServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);
        ChalmersMapsAPIInterface chalmersMapsAPIInterface = mock(ChalmersMapsAPIInterface.class);

        JsonObject expected = new JsonObject();

        when(chalmersMapsAPIInterface.getGeoJsonPOI()).thenReturn(expected);

        GetARoomFacade getARoomFacade = new GetARoomFacade(routeServiceInterface, searchServiceInterface, chalmersMapsAPIInterface);

        JsonObject result = getARoomFacade.getGeoJsonPOI();

        assertEquals(expected, result);
    }

    @Test
    void getGeoJsonBuildings_shouldReturnProperResults() throws IOException {
        SearchServiceInterface searchServiceInterface = mock(SearchServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);
        ChalmersMapsAPIInterface chalmersMapsAPIInterface = mock(ChalmersMapsAPIInterface.class);

        JsonObject expected = new JsonObject();

        when(chalmersMapsAPIInterface.getGeoJsonBuildings()).thenReturn(expected);

        GetARoomFacade getARoomFacade = new GetARoomFacade(routeServiceInterface, searchServiceInterface, chalmersMapsAPIInterface);

        JsonObject result = getARoomFacade.getGeoJsonBuildings();

        assertEquals(expected, result);

    }
}