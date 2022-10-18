package com.chalmers.getaroom.controllers;

import com.chalmers.getaroom.models.objects.Location;
import com.chalmers.getaroom.views.components.GeolocationComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

/**
 * Controller for the GeolocationComponent component
 */
@Component
@UIScope
public class GeolocationComponentController {

    private final GeolocationComponent geolocation;

    public GeolocationComponentController(GeolocationComponent component) {
        this.geolocation = component;
    }

    /**
     * Gets the last tracked location of the user
     *
     * @return The last tracked location of the user
     */
    public Location getLocation() {
        return new Location(geolocation.getLatitude(), geolocation.getLongitude());
    }
}
