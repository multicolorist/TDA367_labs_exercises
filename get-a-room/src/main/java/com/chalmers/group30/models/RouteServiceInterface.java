package com.chalmers.group30.models;

import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Route;

import java.io.IOException;

public interface RouteServiceInterface {
    Route getRoute(Location origin, Location destination) throws IOException;
    double getWalkingDistance(Location origin, Location destination) throws IOException;
    double getBirdsDistance(Location origin, Location destination);
}
