package com.chalmers.group30.controllers;

import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.views.components.GeolocationComponent;

public class GeolocationComponentController {

    private final GeolocationComponent geolocation;

    public GeolocationComponentController(GeolocationComponent component) {
        this.geolocation = component;
    }

    public Location getLocation() {
        return new Location(geolocation.getLatitude(), geolocation.getLongitude());
    }
}
