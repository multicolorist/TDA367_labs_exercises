package com.chalmers.group30.controllers;

import com.chalmers.group30.models.RoomServiceInterface;
import com.chalmers.group30.models.RouteServiceInterface;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Room;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the list of Rooms displayed
 */
@Component
@UIScope
public class RecordListController {
    //TODO: REMOVE THIS ENTIRELY, WILL ONLY NEED QUERYCONTAINER
    private final RoomServiceInterface roomService;
    private final RouteServiceInterface routeService;

    @Autowired
    public RecordListController(RoomServiceInterface roomService, RouteServiceInterface routeService) {
        this.roomService = roomService;
        this.routeService = routeService;
    }

    /**
     * Gets the as-the-crow-flies distance in meters between two positions from the model
     * @param origin The origin location
     * @param destination The destination location
     * @return The distance in meters between the position and the room
     */
    public double getBirdsDistance(Location origin, Location destination) {
        return routeService.getBirdsDistance(origin, destination);
    }

    /**
     * Gets rooms from the model
     * @return A list of Rooms
     */
    public List<Room> getRooms() throws IOException {
        return roomService.getRooms();
    }
}
