package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Route;

import java.io.IOException;

/**
 * Interface defining how a route service should behave
 */
interface RouteServiceInterface {
    /**
     * Gets the route between two locations
     *
     * @param origin      The origin location
     * @param destination The destination location
     * @return A Route object representing the route
     * @throws IOException If an API request failed for some reason.
     */
    Route getRoute(Location origin, Location destination) throws IOException;

    /**
     * Calculates the walking distance in meters between two positions
     *
     * @param origin      The origin location
     * @param destination The destination location
     * @return The distance to the room in meters
     * @throws IOException If an API request failed for some reason.
     */
    double getWalkingDistance(Location origin, Location destination) throws IOException;

    /**
     * Calculates the as-the-crow-flies distance in meters between a given position and a room.
     *
     * @param origin      The origin location
     * @param destination The destination location
     * @return The distance in meters between the position and the room
     */
    double getBirdsDistance(Location origin, Location destination);
}
