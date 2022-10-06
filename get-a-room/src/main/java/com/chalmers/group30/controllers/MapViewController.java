package com.chalmers.group30.controllers;

import com.chalmers.group30.models.ChalmersMapsAPI;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.objects.Route;
import com.chalmers.group30.views.components.MapView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapViewController {
    MapView mapView;

    /**
     * Constructor for the MapViewController
     * @param mapView the MapView to control
     */
    public MapViewController(MapView mapView) {
        // TODO: init buildings etc
        this.mapView = mapView;

        try {
            mapView.addGeoJSON("buildings", new ChalmersMapsAPI().geoJsonBuildings());
            mapView.addExtrusionLayer("buildings_extrude", "buildings");
        } catch (IOException e) {
            //TODO: Add logging
        }

        try {
            mapView.addGeoJSON("poi", new ChalmersMapsAPI().geoJsonPOI());
            mapView.addExtrusionLayer("poi_extrude", "poi");
        } catch (IOException e) {
            //TODO: Add logging
        }
    }

    /**
     * Fly to the given location
     * @param location the location to fly to
     */
    public void flyTo(Location location) {
        mapView.flyTo(location.latitude(), location.longitude());
    }

    /**
     * Display a route on the map. Only allows one route at a time. Showing a new route will remove the old.
     * @param r A route to be displayed
     */
    public void showRoute(Route r) {
        List<List<Double>> maneuvers = new ArrayList<List<Double>>();

        // Convert route maneuvers into list of coordinate pairs
        for(Location l: r.maneuvers()) {
            maneuvers.add(List.of(l.latitude(), l.longitude()));
        }
        mapView.showRoute(maneuvers);
    }

}
