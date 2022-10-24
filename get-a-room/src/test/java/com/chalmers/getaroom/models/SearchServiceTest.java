package com.chalmers.getaroom.models;

import com.chalmers.getaroom.models.objects.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SearchServiceTest {

    @Test
    void search_shouldReturnProperResultsWhenSortingByDistance() throws IOException {
        RoomServiceInterface roomServiceInterface = mock(RoomServiceInterface.class);
        BookingServiceInterface bookingServiceInterface = mock(BookingServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);

        List<Room> rooms = new ArrayList<>(List.of(
                new Room("Test 1", 2, "Building", "Floor 2", "Address", "chalmers:test1", UUID.fromString("00000000-0000-0000-0000-000000000001"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 2", 1, "Building", "Floor 2", "Address", "chalmers:test2", UUID.fromString("00000000-0000-0000-0000-000000000002"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 3", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000003"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 4", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000004"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 5", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000005"), new Location(1, 1), new Location(1, 1))));

        when(roomServiceInterface.getRooms()).thenReturn(rooms);

        when(bookingServiceInterface.getBookings(rooms.get(0))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 12, 0), LocalDateTime.of(2022, 1, 1, 13, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(1))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 15, 0), LocalDateTime.of(2022, 1, 1, 16, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(2))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 11, 0), LocalDateTime.of(2022, 1, 1, 12, 30))));
        when(bookingServiceInterface.getBookings(rooms.get(3))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 12, 30), LocalDateTime.of(2022, 1, 1, 16, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(4))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 16, 30), LocalDateTime.of(2022, 1, 1, 17, 0))));

        when(routeServiceInterface.getBirdsDistance(any(), any())).thenReturn(1000.0);

        SearchServiceInterface searchServiceInterface = new SearchService(bookingServiceInterface, roomServiceInterface, routeServiceInterface);

        SearchQuery query = new SearchQuery(new Location(1, 1), 2, LocalDateTime.of(2022, 1, 1, 12, 15), LocalDateTime.of(2022, 1, 1, 13, 0));

        SearchResult searchResult = searchServiceInterface.search(query);

        assertEquals(query, searchResult.searchQuery());
        assertEquals(1, searchResult.results().size());
        assertEquals(rooms.get(4), searchResult.results().get(0).room());
        assertEquals(1000.0, searchResult.results().get(0).birdsDistance());
        assertNotEquals(0, searchResult.results().get(0).bookings().size());
    }

    @Test
    void search_shouldReturnProperResultsWhenSortingByName() throws IOException {
        RoomServiceInterface roomServiceInterface = mock(RoomServiceInterface.class);
        BookingServiceInterface bookingServiceInterface = mock(BookingServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);

        List<Room> rooms = new ArrayList<>(List.of(
                new Room("Test 1", 2, "Building", "Floor 2", "Address", "chalmers:test1", UUID.fromString("00000000-0000-0000-0000-000000000001"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 2", 1, "Building", "Floor 2", "Address", "chalmers:test2", UUID.fromString("00000000-0000-0000-0000-000000000002"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 3", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000003"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 4", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000004"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 5", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000005"), new Location(1, 1), new Location(1, 1))));

        when(roomServiceInterface.getRooms()).thenReturn(rooms);

        when(bookingServiceInterface.getBookings(rooms.get(0))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 12, 0), LocalDateTime.of(2022, 1, 1, 13, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(1))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 15, 0), LocalDateTime.of(2022, 1, 1, 16, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(2))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 11, 0), LocalDateTime.of(2022, 1, 1, 12, 30))));
        when(bookingServiceInterface.getBookings(rooms.get(3))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 12, 30), LocalDateTime.of(2022, 1, 1, 16, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(4))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 16, 30), LocalDateTime.of(2022, 1, 1, 17, 0))));

        when(routeServiceInterface.getBirdsDistance(any(), any())).thenReturn(1000.0);

        SearchServiceInterface searchServiceInterface = new SearchService(bookingServiceInterface, roomServiceInterface, routeServiceInterface);

        SearchQuery query = new SearchQuery(new Location(Double.NaN, Double.NaN), 2, LocalDateTime.of(2022, 1, 1, 12, 15), LocalDateTime.of(2022, 1, 1, 13, 0));

        SearchResult searchResult = searchServiceInterface.search(query);

        assertEquals(query, searchResult.searchQuery());
        assertEquals(1, searchResult.results().size());
        assertEquals(rooms.get(4), searchResult.results().get(0).room());
        assertEquals(Double.NaN, searchResult.results().get(0).birdsDistance());
        assertNotEquals(0, searchResult.results().get(0).bookings().size());
    }

    @Test
    void search_shouldThrowIllegalArgumentExceptionOnGroupSizeSmallerThanOne() throws IOException {
        RoomServiceInterface roomServiceInterface = mock(RoomServiceInterface.class);
        BookingServiceInterface bookingServiceInterface = mock(BookingServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);

        List<Room> rooms = new ArrayList<>(List.of(
                new Room("Test 1", 2, "Building", "Floor 2", "Address", "chalmers:test1", UUID.fromString("00000000-0000-0000-0000-000000000001"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 2", 1, "Building", "Floor 2", "Address", "chalmers:test2", UUID.fromString("00000000-0000-0000-0000-000000000002"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 3", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000003"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 4", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000004"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 5", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000005"), new Location(1, 1), new Location(1, 1))));

        when(roomServiceInterface.getRooms()).thenReturn(rooms);

        when(bookingServiceInterface.getBookings(rooms.get(0))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 12, 0), LocalDateTime.of(2022, 1, 1, 13, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(1))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 15, 0), LocalDateTime.of(2022, 1, 1, 16, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(2))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 11, 0), LocalDateTime.of(2022, 1, 1, 12, 30))));
        when(bookingServiceInterface.getBookings(rooms.get(3))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 12, 30), LocalDateTime.of(2022, 1, 1, 16, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(4))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 16, 30), LocalDateTime.of(2022, 1, 1, 17, 0))));

        when(routeServiceInterface.getBirdsDistance(any(), any())).thenReturn(1000.0);

        SearchServiceInterface searchServiceInterface = new SearchService(bookingServiceInterface, roomServiceInterface, routeServiceInterface);

        SearchQuery query = new SearchQuery(new Location(1, 1), 0, LocalDateTime.of(2022, 1, 1, 12, 15), LocalDateTime.of(2022, 1, 1, 13, 0));

        assertThrows(IllegalArgumentException.class, () -> searchServiceInterface.search(query));
    }

    @Test
    void search_shouldThrowIllegalArgumentExceptionOnStartTimeAfterEndTime() throws IOException {
        RoomServiceInterface roomServiceInterface = mock(RoomServiceInterface.class);
        BookingServiceInterface bookingServiceInterface = mock(BookingServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);

        List<Room> rooms = new ArrayList<>(List.of(
                new Room("Test 1", 2, "Building", "Floor 2", "Address", "chalmers:test1", UUID.fromString("00000000-0000-0000-0000-000000000001"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 2", 1, "Building", "Floor 2", "Address", "chalmers:test2", UUID.fromString("00000000-0000-0000-0000-000000000002"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 3", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000003"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 4", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000004"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 5", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000005"), new Location(1, 1), new Location(1, 1))));

        when(roomServiceInterface.getRooms()).thenReturn(rooms);

        when(bookingServiceInterface.getBookings(rooms.get(0))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 12, 0), LocalDateTime.of(2022, 1, 1, 13, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(1))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 15, 0), LocalDateTime.of(2022, 1, 1, 16, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(2))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 11, 0), LocalDateTime.of(2022, 1, 1, 12, 30))));
        when(bookingServiceInterface.getBookings(rooms.get(3))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 12, 30), LocalDateTime.of(2022, 1, 1, 16, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(4))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 16, 30), LocalDateTime.of(2022, 1, 1, 17, 0))));

        when(routeServiceInterface.getBirdsDistance(any(), any())).thenReturn(1000.0);

        SearchServiceInterface searchServiceInterface = new SearchService(bookingServiceInterface, roomServiceInterface, routeServiceInterface);

        SearchQuery query = new SearchQuery(new Location(1, 1), 1, LocalDateTime.of(2022, 1, 1, 12, 15), LocalDateTime.of(2021, 1, 1, 13, 0));

        assertThrows(IllegalArgumentException.class, () -> searchServiceInterface.search(query));
    }

    @Test
    void search_shouldSkipRoomIfFailToGetBookings() throws IOException {
        RoomServiceInterface roomServiceInterface = mock(RoomServiceInterface.class);
        BookingServiceInterface bookingServiceInterface = mock(BookingServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);

        List<Room> rooms = new ArrayList<>(List.of(
                new Room("Test 1", 2, "Building", "Floor 2", "Address", "chalmers:test1", UUID.fromString("00000000-0000-0000-0000-000000000001"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 2", 1, "Building", "Floor 2", "Address", "chalmers:test2", UUID.fromString("00000000-0000-0000-0000-000000000002"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 3", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000003"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 4", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000004"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 5", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000005"), new Location(1, 1), new Location(1, 1))));

        when(roomServiceInterface.getRooms()).thenReturn(rooms);

        when(bookingServiceInterface.getBookings(rooms.get(0))).thenThrow(IOException.class);

        when(routeServiceInterface.getBirdsDistance(any(), any())).thenReturn(1000.0);

        SearchServiceInterface searchServiceInterface = new SearchService(bookingServiceInterface, roomServiceInterface, routeServiceInterface);

        SearchQuery query = new SearchQuery(new Location(1, 1), 1, LocalDateTime.of(2021, 1, 1, 12, 15), LocalDateTime.of(2021, 1, 1, 13, 0));

        searchServiceInterface.search(query);
    }

    @Test
    void search_shouldFailOnGetRoomsFail() throws IOException {
        RoomServiceInterface roomServiceInterface = mock(RoomServiceInterface.class);
        BookingServiceInterface bookingServiceInterface = mock(BookingServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);

        List<Room> rooms = new ArrayList<>(List.of(
                new Room("Test 1", 2, "Building", "Floor 2", "Address", "chalmers:test1", UUID.fromString("00000000-0000-0000-0000-000000000001"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 2", 1, "Building", "Floor 2", "Address", "chalmers:test2", UUID.fromString("00000000-0000-0000-0000-000000000002"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 3", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000003"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 4", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000004"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 5", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000005"), new Location(1, 1), new Location(1, 1))));

        when(roomServiceInterface.getRooms()).thenThrow(IOException.class);

        when(bookingServiceInterface.getBookings(rooms.get(0))).thenThrow(IOException.class);

        when(routeServiceInterface.getBirdsDistance(any(), any())).thenReturn(1000.0);

        SearchServiceInterface searchServiceInterface = new SearchService(bookingServiceInterface, roomServiceInterface, routeServiceInterface);

        SearchQuery query = new SearchQuery(new Location(1, 1), 1, LocalDateTime.of(2021, 1, 1, 12, 15), LocalDateTime.of(2021, 1, 1, 13, 0));

        assertThrows(IOException.class, () -> searchServiceInterface.search(query));
    }

    @Test
    void search_shouldSkipRoomIfBirdsDistanceFails() throws IOException {
        RoomServiceInterface roomServiceInterface = mock(RoomServiceInterface.class);
        BookingServiceInterface bookingServiceInterface = mock(BookingServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);

        List<Room> rooms = new ArrayList<>(List.of(
                new Room("Test 1", 2, "Building", "Floor 2", "Address", "chalmers:test1", UUID.fromString("00000000-0000-0000-0000-000000000001"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 2", 1, "Building", "Floor 2", "Address", "chalmers:test2", UUID.fromString("00000000-0000-0000-0000-000000000002"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 3", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000003"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 4", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000004"), new Location(1, 1), new Location(1, 1)),
                new Room("Test 5", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000005"), new Location(1, 1), new Location(1, 1))));

        when(roomServiceInterface.getRooms()).thenReturn(rooms);

        when(bookingServiceInterface.getBookings(rooms.get(0))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 12, 0), LocalDateTime.of(2022, 1, 1, 13, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(1))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 15, 0), LocalDateTime.of(2022, 1, 1, 16, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(2))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 11, 0), LocalDateTime.of(2022, 1, 1, 12, 30))));
        when(bookingServiceInterface.getBookings(rooms.get(3))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 12, 30), LocalDateTime.of(2022, 1, 1, 16, 0))));
        when(bookingServiceInterface.getBookings(rooms.get(4))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 1, 1, 16, 30), LocalDateTime.of(2022, 1, 1, 17, 0))));

        when(routeServiceInterface.getBirdsDistance(any(), any())).thenThrow(RuntimeException.class);

        SearchServiceInterface searchServiceInterface = new SearchService(bookingServiceInterface, roomServiceInterface, routeServiceInterface);

        SearchQuery query = new SearchQuery(new Location(1, 1), 1, LocalDateTime.of(2021, 1, 1, 12, 15), LocalDateTime.of(2021, 1, 1, 13, 0));

        searchServiceInterface.search(query);
    }

}