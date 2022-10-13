package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.*;
import net.fortuna.ical4j.data.ParserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Service for searching for available rooms
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class SearchService implements SearchServiceInterface {

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
     * @param searchQuery The search query
     * @return A list of matching rooms with a distance from the user and all existing bookings for the room
     * @throws IOException If the API request failed for some reason.
     * @throws IllegalArgumentException If ether the group size is less than 1 or the start time is after the end time
     */
    @Override
    public SearchResult search(SearchQuery searchQuery) throws IOException, IllegalArgumentException {

        if (searchQuery.groupSize() < 1) {
            throw new IllegalArgumentException("Group size must be at least 1");
        }

        if (searchQuery.startTime().isAfter(searchQuery.endTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        try {
            List<Room> candidateRooms = roomService.getRooms();
            List<Room> matchingRooms = new ArrayList<>();
            roomLoop:
            for (Room room : candidateRooms) {

                //todo Include the rooms with unknown size? (marked with -1)
                if (room.seats() < searchQuery.groupSize()) {
                    continue;
                }

                try {
                    List<Booking> bookings = bookingService.getBookings(room);

                    for (Booking booking : bookings) {
                        //Booking start time is within the search time
                        if (((booking.startTime().isAfter(searchQuery.startTime()) || booking.startTime().isEqual(searchQuery.startTime())) && booking.startTime().isBefore(searchQuery.endTime()))
                                //or Booking end time is within the search time
                                || (booking.endTime().isAfter(searchQuery.startTime()) && (booking.endTime().isBefore(searchQuery.endTime()) || booking.endTime().isEqual(searchQuery.endTime())))) {
                            continue roomLoop;
                        }
                    }
                    matchingRooms.add(room);

                }catch (Exception e){
                    logger.log(Level.WARNING, "Failed to get bookings for room: " + room.name() + " with UUID " + room.uuid() + ". Skipping room for current search.", e);
                    continue;
                }

            }

            List<SearchRecord> results = new ArrayList<>();
            for (Room room : matchingRooms) {

                try {
                    results.add(new SearchRecord(room, bookingService.getBookings(room),
                            searchQuery.userLocation() != null ? routeService.getBirdsDistance(searchQuery.userLocation(), room.location()) : Double.NaN));
                } catch (Exception e) {
                    logger.log(Level.WARNING, "Failed to get bookings for room: " + room.name() + " with UUID " + room.uuid() + ". Skipping room for current search.", e);
                }
            }

            if (searchQuery.userLocation() == null){
                results.sort(Comparator.comparing(o -> o.room().name()));
            }else {
                results.sort(Comparator.comparing(o -> o.birdsDistance()));
            }

            return new SearchResult(searchQuery, results);
        }catch (Exception e){
            logger.log(Level.SEVERE, "Failed to search for bookings.", e);
            throw e;
        }
    }
}
