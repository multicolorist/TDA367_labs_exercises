package com.chalmers.getaroom.models;

import com.chalmers.getaroom.models.objects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for searching for available rooms
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.NO)
class SearchService implements SearchServiceInterface {

    private final BookingServiceInterface bookingService;
    private final RoomServiceInterface roomService;
    private final RouteServiceInterface routeService;
    private final Logger logger = Logger.getLogger(SearchService.class.getName());

    @Autowired
    public SearchService(BookingServiceInterface bookingService, RoomServiceInterface roomService, RouteServiceInterface routeService) {
        this.bookingService = bookingService;
        this.roomService = roomService;
        this.routeService = routeService;
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
        validateSearchQuery(searchQuery);

        try {
            List<Room> candidateRooms = roomService.getRooms();
            List<Room> matchingRooms = getMatchingRooms(searchQuery, candidateRooms);
            List<SearchRecord> results = generateSearchRecords(searchQuery, matchingRooms);

            if (Double.isNaN(searchQuery.userLocation().latitude()) || Double.isNaN(searchQuery.userLocation().longitude())) {
                results.sort(Comparator.comparing(o -> o.room().name()));
            } else {
                results.sort(Comparator.comparing(o -> o.birdsDistance()));
            }
            return new SearchResult(searchQuery, results);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to search for bookings.", e);
            throw e;
        }
    }

    private List<SearchRecord> generateSearchRecords(SearchQuery searchQuery, List<Room> matchingRooms) {
        List<SearchRecord> results = new ArrayList<>();
        for (Room room : matchingRooms) {

            try {
                results.add(new SearchRecord(room, bookingService.getBookings(room),
                        !Double.isNaN(searchQuery.userLocation().latitude()) && !Double.isNaN(searchQuery.userLocation().longitude()) ? routeService.getBirdsDistance(searchQuery.userLocation(), room.location()) : Double.NaN));
            } catch (Exception e) {
                logger.log(Level.WARNING, "Failed to get bookings for room: " + room.name() + " with UUID " + room.uuid() + ". Skipping room for current search.", e);
            }
        }
        return results;
    }

    private List<Room> getMatchingRooms(SearchQuery searchQuery, List<Room> candidateRooms) {
        List<Room> matchingRooms = new ArrayList<>();
        roomLoop:
        for (Room room : candidateRooms) {
            if (room.seats() < searchQuery.groupSize()) {
                continue;
            }

            try {
                List<Booking> bookings = bookingService.getBookings(room);

                for (Booking booking : bookings) {
                    if (hasTimeOverlap(searchQuery, booking)) {
                        continue roomLoop;
                    }
                }
                matchingRooms.add(room);

            } catch (Exception e) {
                logger.log(Level.WARNING, "Failed to get bookings for room: " + room.name() + " with UUID " + room.uuid() + ". Skipping room for current search.", e);
            }

        }
        return matchingRooms;
    }

    private static boolean hasTimeOverlap(SearchQuery searchQuery, Booking booking) {
        return !searchQuery.endTime().isBefore(booking.startTime()) && !searchQuery.startTime().isAfter(booking.endTime());
    }

    private void validateSearchQuery(SearchQuery searchQuery) {
        if (searchQuery.groupSize() < 1) {
            throw new IllegalArgumentException("Group size must be at least 1");
        }

        if (searchQuery.startTime().isAfter(searchQuery.endTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
    }
}
