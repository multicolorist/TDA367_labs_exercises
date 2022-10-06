package com.chalmers.group30.controllers;

import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.views.components.GeolocationComponent;

/**
 * Controller for the GeolocationComponent component
 */
public class GeolocationComponentController {

    private final GeolocationComponent geolocation;

    public GeolocationComponentController(GeolocationComponent component) {
        this.geolocation = component;
    }

    /**
     * Gets the last tracked location of the user
     * @return The last tracked location of the user
     */
    public Location getLocation() {
        return new Location(geolocation.getLatitude(), geolocation.getLongitude());
    }
}
