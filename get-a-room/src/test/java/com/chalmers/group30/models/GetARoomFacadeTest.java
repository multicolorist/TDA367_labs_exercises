package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetARoomFacadeTest {

    @Test
    void search_shouldReturnProperResults() throws IOException, ParseException {
        SearchServiceInterface searchServiceInterface = mock(SearchServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);

        SearchQuery query = new SearchQuery(new Location(1,1), 2, LocalDateTime.of(2022,01,01,12,15), LocalDateTime.of(2022,01,01,13,00));

        SearchResult expectedSearchResult = new SearchResult(query, List.of(
                new SearchRecord(
                        new Room("Test 1", 2, "Building", "Floor 2", "Address",
                                "chalmers:test1", UUID.fromString("00000000-0000-0000-0000-000000000001"),
                                new Location(1,1),
                                new Location(1,1)),
                        List.of(new Booking(LocalDateTime.of(2022, 01, 01, 12, 00),
                                LocalDateTime.of(2022, 01, 01, 13, 00))),
                        1000)));



        when(searchServiceInterface.search(query)).thenReturn(expectedSearchResult);

        GetARoomFacade getARoomFacade = new GetARoomFacade(routeServiceInterface, searchServiceInterface);


        SearchResult searchResult = getARoomFacade.search(query);

        assertEquals(expectedSearchResult, searchResult);

    }

    @Test
    void getWalkingRoute_shouldReturnProperResults() throws IOException {
        SearchServiceInterface searchServiceInterface = mock(SearchServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);

        Location userLocation = new Location(1,1);
        Location destinationLocation = new Location(2,2);

        Route route = new Route(1000.0, Arrays.asList(new Location(1,1), new Location(2,2)));

        when(routeServiceInterface.getRoute(userLocation, destinationLocation)).thenReturn(route);

        GetARoomFacade getARoomFacade = new GetARoomFacade(routeServiceInterface, searchServiceInterface);

        Route result = getARoomFacade.getWalkingRoute(userLocation, destinationLocation);

        assertEquals(route, result);

    }
}