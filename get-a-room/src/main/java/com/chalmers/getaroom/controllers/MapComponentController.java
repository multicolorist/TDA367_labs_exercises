package com.chalmers.getaroom.controllers;

import com.chalmers.getaroom.models.GetARoomFacadeInterface;
import com.chalmers.getaroom.models.objects.Location;
import com.chalmers.getaroom.models.objects.Route;
import com.chalmers.getaroom.views.components.MapComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the MapView component
 */
@Component
@UIScope
public class MapComponentController {
    private final MapComponent mapComponent;

    private final Logger logger = Logger.getLogger(MapComponentController.class.getName());

    /**
     * Constructor for the MapViewController
     *
     * @param mapComponent the MapView to control
     */
    public MapComponentController(MapComponent mapComponent, GetARoomFacadeInterface getARoomFacade) {
        this.mapComponent = mapComponent;

        try {
            mapComponent.addGeoJSON("buildings", getARoomFacade.getGeoJsonBuildings());
            mapComponent.addExtrusionLayer("buildings_extrude", "buildings");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not get geoJSON buildings.", e);
        }

        try {
            mapComponent.addGeoJSON("poi", getARoomFacade.getGeoJsonPOI());
            mapComponent.addExtrusionLayer("poi_extrude", "poi");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not get geoJSON POIs.", e);
        }
    }

    /**
     * Fly to the given location
     *
     * @param location the location to fly to
     */
    public void flyTo(Location location) {
        mapComponent.flyTo(location.latitude(), location.longitude());
    }

    /**
     * Display a route on the map. Only allows one route at a time. Showing a new route will remove the old.
     *
     * @param r A route to be displayed
     */
    public void showRoute(Route r) {
        List<List<Double>> maneuvers = new ArrayList<List<Double>>();

        // Convert route maneuvers into list of coordinate pairs
        for (Location l : r.maneuvers()) {
            maneuvers.add(List.of(l.latitude(), l.longitude()));
        }
        mapComponent.showRoute(maneuvers);
    }

}
