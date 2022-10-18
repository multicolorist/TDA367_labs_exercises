package com.chalmers.group30.controllers;

import com.chalmers.group30.models.GetARoomFacadeInterface;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Route;
import com.chalmers.group30.views.components.MapView;
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
public class MapViewController {
    MapView mapView;

    GetARoomFacadeInterface getARoomFacade;
    private final Logger logger = Logger.getLogger(MapViewController.class.getName());

    /**
     * Constructor for the MapViewController
     *
     * @param mapView the MapView to control
     */
    public MapViewController(MapView mapView, GetARoomFacadeInterface getARoomFacade) {
        this.mapView = mapView;
        this.getARoomFacade = getARoomFacade;

        try {
            mapView.addGeoJSON("buildings", getARoomFacade.geoJsonBuildings());
            mapView.addExtrusionLayer("buildings_extrude", "buildings");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not get geoJSON buildings.", e);
        }

        try {
            mapView.addGeoJSON("poi", getARoomFacade.geoJsonPOI());
            mapView.addExtrusionLayer("poi_extrude", "poi");
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
        mapView.flyTo(location.latitude(), location.longitude());
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
        mapView.showRoute(maneuvers);
    }

}
