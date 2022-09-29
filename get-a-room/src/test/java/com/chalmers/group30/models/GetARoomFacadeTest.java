package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.objects.SearchRecord;
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
        RoomServiceInterface roomServiceInterface = mock(RoomServiceInterface.class);
        BookingServiceInterface bookingServiceInterface = mock(BookingServiceInterface.class);
        RouteServiceInterface routeServiceInterface = mock(RouteServiceInterface.class);

        List<Room> rooms = new ArrayList<>(List.of(
                new Room("Test 1", 2, "Building", "Floor 2", "Address", "chalmers:test1", UUID.fromString("00000000-0000-0000-0000-000000000001"), new Location(1,1), new Location(1,1)),
                new Room("Test 2", 1, "Building", "Floor 2", "Address", "chalmers:test2", UUID.fromString("00000000-0000-0000-0000-000000000002"), new Location(1,1), new Location(1,1)),
                new Room("Test 3", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000003"), new Location(1,1), new Location(1,1)),
                new Room("Test 4", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000004"), new Location(1,1), new Location(1,1)),
                new Room("Test 5", 2, "Building", "Floor 2", "Address", "chalmers:test3", UUID.fromString("00000000-0000-0000-0000-000000000005"), new Location(1,1), new Location(1,1))));

        when(roomServiceInterface.getRooms()).thenReturn(rooms);

        when(bookingServiceInterface.getBookings(rooms.get(0))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 01, 01, 12, 00), LocalDateTime.of(2022, 01, 01, 13, 00))));
        when(bookingServiceInterface.getBookings(rooms.get(1))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 01, 01, 15, 00), LocalDateTime.of(2022, 01, 01, 16, 00))));
        when(bookingServiceInterface.getBookings(rooms.get(2))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 01, 01, 11, 00), LocalDateTime.of(2022, 01, 01, 12, 30))));
        when(bookingServiceInterface.getBookings(rooms.get(3))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 01, 01, 12, 30), LocalDateTime.of(2022, 01, 01, 16, 00))));
        when(bookingServiceInterface.getBookings(rooms.get(4))).thenReturn(List.of(new Booking(LocalDateTime.of(2022, 01, 01, 16, 30), LocalDateTime.of(2022, 01, 01, 17, 00))));

        when(routeServiceInterface.getBirdsDistance(any(), any())).thenReturn(1000.0);

        GetARoomFacade getARoomFacade = new GetARoomFacade(bookingServiceInterface, roomServiceInterface, routeServiceInterface);

        List<SearchRecord> searchRecords = getARoomFacade.search(new Location(1,1), 2, LocalDateTime.of(2022,01,01,12,15), LocalDateTime.of(2022,01,01,13,00));

        assertEquals(1, searchRecords.size());
        assertEquals(rooms.get(4) ,searchRecords.get(0).room());
        assertEquals(1000.0, searchRecords.get(0).birdsDistance());
        assertNotEquals(0, searchRecords.get(0).bookings().size());
    }

    @Test
    void getWalkingRoute() {
    }
}