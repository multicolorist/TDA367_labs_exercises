package com.chalmers.getaroom.views.components;

import com.google.gson.JsonObject;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.spring.annotation.UIScope;

import java.util.List;

/**
 * Represents a MapLibre component to be used in Vaadin
 */
@Tag("maplibre-gl-js")
@NpmPackage(value = "maplibre-gl", version = "2.4.0")
@JsModule("./maplibre/maplibre.ts")
@UIScope
@org.springframework.stereotype.Component
public class MapView extends Component implements HasSize, HasStyle, HasComponents {
    /**
     * Add and display a room on the map
     *
     * @param name      the name to be displayed
     * @param latitude  the latitude to display the room
     * @param longitude the longitude to display the room
     */
    public void addRoom(String name, double latitude, double longitude) {
        getElement().executeJs("this.addRoom('" + name + "', " + latitude + ", " + longitude + ");");
    }

    /**
     * Remove the currently displayed room from the map
     */
    public void removeRoom() {
        getElement().executeJs("this.removeRoom();");
    }

    /**
     * Display a route on the map. Only allows one route at a time. Showing a new route will remove the old.
     *
     * @param maneuvers A list of latitude-longitude pairs
     */
    public void showRoute(List<List<Double>> maneuvers) {
        // Generate JS object of locations
        StringBuilder locationArrayString = new StringBuilder("[");
        for (int i = 0; i < maneuvers.size(); i++) {
            locationArrayString.append("[").append(maneuvers.get(i).get(1)).append(",").append(maneuvers.get(i).get(0)).append("],");
        }
        // Remove final stray comma
        locationArrayString.deleteCharAt(locationArrayString.length() - 1);
        locationArrayString.append("]");

        // Add a point representing the route destination
        List<Double> finalLoc = maneuvers.get(maneuvers.size() - 1);
        String finalLocationArrayString = "[" + finalLoc.get(1) + "," + finalLoc.get(0) + "]";

        // Show route on the map
        getElement().executeJs("this.showRoute(" + locationArrayString.toString() + ", " + finalLocationArrayString + ");");
    }

    /**
     * Remove any route if it's currently visible
     */
    public void removeRoute() {
        getElement().executeJs("this.removeRoute();");
    }

    /**
     * Remove currently displayed rooms and routes on the map.
     */
    public void clearRoomsAndRoutes() {
        getElement().executeJs("this.clearRoomsAndRoutes();");
    }

    /**
     * Fly to the coordinates given
     *
     * @param latitude  the latitude to fly to
     * @param longitude the longitude to fly to
     */
    public void flyTo(double latitude, double longitude) {
        getElement().executeJs("this.flyTo(" + longitude + ", " + latitude + ");");
    }

    /**
     * Add a GeoJSON source to the map
     *
     * @param id  The identifier of the source
     * @param obj The GeoJSON object
     */
    public void addGeoJSON(String id, JsonObject obj) {
        getElement().executeJs("this.addGeoJSON('" + id + "', " + obj.toString() + ");");
    }

    /**
     * Add an extruded polygon layer to the map
     *
     * @param id     The identifier of the layer
     * @param source The GeoJSON source ID
     */
    public void addExtrusionLayer(String id, String source) {
        getElement().executeJs("this.addExtrudedLayer('" + id + "', '" + source + "');");
    }
}
