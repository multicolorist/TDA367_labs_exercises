package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Route;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Provides routes and distances from the API
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.NO)
public class RouteService implements RouteServiceInterface {

    private ChalmersMapsAPIInterface chalmersMapsAPI;
    private final Logger logger = Logger.getLogger(RouteService.class.getName());

    @Autowired
    public RouteService(ChalmersMapsAPIInterface chalmersMapsAPI){
        this.chalmersMapsAPI = chalmersMapsAPI;
    }

    /**
     * Gets the route between two locations
     * @param origin The origin location
     * @param destination The destination location
     * @return A Route object representing the route
     * @throws IOException If an API request failed for some reason.
     */
    public Route getRoute(Location origin, Location destination) throws IOException {
        try {
            JsonObject route = chalmersMapsAPI.route(origin, destination);
            return Route.fromJSON(route);
        }catch (IOException e){
            logger.log(java.util.logging.Level.SEVERE, "Failed to get route from API", e);
            throw e;
        }
    }

    /**
     * Calculates the walking distance in meters between two positions
     * @param origin The origin location
     * @param destination The destination location
     * @return The distance to the room in meters
     * @throws IOException If an API request failed for some reason.
     */
    public double getWalkingDistance(Location origin, Location destination) throws IOException {
        Route route = getRoute(origin, destination);
        return route.distance();
    }

    /**
     * Calculates the as-the-crow-flies distance in meters between a given position and a room.
     * @param origin The origin location
     * @param destination The destination location
     * @return The distance in meters between the position and the room
     */
    public double getBirdsDistance(Location origin, Location destination) {
        // Example coordinates:
        // 'longitude': 11.97489,
        // 'latitude': 57.691049,

        // Using the haversine formula - works well also for small distances:
        // https://www.movable-type.co.uk/scripts/latlong.html
        double R = 6371e3; // earth radius
        double phi1 = Math.toRadians(destination.latitude());
        double phi2 = Math.toRadians(origin.latitude());
        double dPhi = Math.toRadians(origin.latitude() - destination.latitude());
        double dLambda = Math.toRadians(origin.longitude() - destination.longitude());

        double a = Math.sin(dPhi/2) * Math.sin(dPhi/2) +
                Math.cos(phi1) * Math.cos(phi2) *
                        Math.sin(dLambda/2) * Math.sin(dLambda/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c;
    }

}
