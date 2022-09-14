package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Booking;
import com.chalmers.group30.models.objects.Room;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Facade for finding rooms to the user - the only front-facing interface
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class BookingService implements BookingServiceInterface{

    // UID for Campus Johanneberg
    // TODO: Use both campuses when basic functionality is there for Johanneberg
    private final static UUID areaUID = UUID.fromString("a85a8be2-4ff6-4e39-9880-c2adb2a7626f");

    private ChalmersMapsAPIInterface chalmersMapsAPI;

    @Autowired
    public BookingService(ChalmersMapsAPIInterface chalmersMapsAPI){
        this.chalmersMapsAPI = chalmersMapsAPI;
    }

    /**
     * Gets currently available rooms
     * @return A list of all rooms
     * @throws IOException If the API request failed for some reason.
     */
    public List<Room> getRooms() throws IOException {
        // Get all buildings and facilities
        JsonObject buildings = chalmersMapsAPI.informationBoard(areaUID);

        // Get all rooms in buildings and facilities
        List<Room> rooms = new ArrayList<>();
        for(JsonElement e : buildings.get("suggestions").getAsJsonArray()) {
            rooms.add(Room.fromJSON(e.getAsJsonObject()));
        }

        // TODO: Filter rooms based on requirements

        return rooms;
    }

    @Override
    public List<Booking> getBookings(Room room, Instant startTime) throws IOException {
//        List<Booking> bookings;
//        startTime.get()
//        int year = startTime.;
//        bookings = chalmersMapsAPI.getBookings(room, startTime);
//                chalmersMapsAPI.timeEditSchedule(room.uuid(), , startTime.plusSeconds(24 * 7 * 4 * 3600));
//
        return null;
    }
}
