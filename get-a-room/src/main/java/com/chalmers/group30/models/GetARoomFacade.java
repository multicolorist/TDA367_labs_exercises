package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GetARoomFacade implements GetARoomFacadeInterface {

    private final BookingServiceInterface bookingService;
    private final RoomServiceInterface roomService;
    private final RouteServiceInterface routeService;

    @Autowired
    public GetARoomFacade(BookingServiceInterface bookingService, RoomServiceInterface roomService, RouteServiceInterface routeService) {

        this.bookingService = bookingService;
        this.roomService = roomService;
        this.routeService = routeService;
    }

    /**
     * Search for matching rooms that are free at the given time
     * @param userLocation The user location, can be null
     * @param groupSize The group size
     * @param startTime The desired start time
     * @param endTime The desired end time
     * @return A list of matching rooms with a distance from the user and all existing bookings for the room
     * @throws IOException If the API request failed for some reason.
     */
    @Override
    public List<SearchRecord> search(Location userLocation, int groupSize, LocalDateTime startTime, LocalDateTime endTime) throws IOException {
        List<Room> candidateRooms = roomService.getRooms();
        List<Room> matchingRooms = new ArrayList<>();
        roomLoop:
        for (Room room : candidateRooms) {

            //todo Include the rooms with unknown size? (marked with -1)
            if (room.seats() < groupSize) {
                continue;
            }

            try {
                List<Booking> bookings = bookingService.getBookings(room);

                for (Booking booking : bookings) {
                    //Booking start time is within the search time
                    if (((booking.startTime().isAfter(startTime) || booking.startTime().isEqual(startTime)) && booking.startTime().isBefore(endTime))
                            //or Booking end time is within the search time
                            || (booking.endTime().isAfter(startTime) && (booking.endTime().isBefore(endTime) || booking.endTime().isEqual(endTime)))) {
                        continue roomLoop;
                    }
                }
                matchingRooms.add(room);

            }catch (Exception e){
                continue;
            }

        }

        List<SearchRecord> results = new ArrayList<>();
        for (Room room : matchingRooms) {

            try {
                results.add(new SearchRecord(room, bookingService.getBookings(room),
                        userLocation != null ? routeService.getBirdsDistance(userLocation, room.location()) : -1));
            } catch (ParseException e) {

            }
        }

        return results;

    }

    @Override
    public Route getWalkingRoute(Location userLocation, Location destinationLocation) throws IOException {
        return routeService.getRoute(userLocation, destinationLocation);
    }
}
