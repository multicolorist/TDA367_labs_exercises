package com.chalmers.group30.views.components;

import com.chalmers.group30.models.ChalmersMapsAPI;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.objects.Route;
import com.google.gson.JsonObject;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.CssImport.Container;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;

@Tag("maplibre-gl-js")
@NpmPackage(value = "maplibre-gl", version = "2.4.0")
@JsModule("./maplibre/maplibre.ts")
/**
 * Represents a MapLibre component to be used in Vaadin
 */
public class MapLibreContainer extends Component implements HasSize, HasStyle, HasComponents {

    /**
     * Add and display a room on the map
     * @param r the room to be displayed
     */
    public void addRoom(Room r) {
        getElement().executeJs("this.addRoom('"+r.uuid()+"', '"+r.name()+"', "+r.location().latitude()+", "+r.location().longitude()+");");
    }

    /**
     * Remove the currently displayed room from the map
     */
    public void removeRoom() {
        getElement().executeJs("this.removeRoom();");
    }

    /**
     * Display a route on the map. Only allows one route at a time. Showing a new route will remove the old.
     * @param r the route to be displayed
     */
    public void showRoute(Route r) {
        // Generate JS object of locations
        StringBuilder locationArrayString = new StringBuilder("[");
        for(Location point : r.maneuvers()){
            locationArrayString.append("[").append(point.longitude()).append(",").append(point.latitude()).append("],");
        }
        // Remove final stray comma
        locationArrayString.deleteCharAt(locationArrayString.length()-1);
        locationArrayString.append("]");

        // Add a point representing the route destination
        Location finalLoc = r.maneuvers().get(r.maneuvers().size() - 1);
        String finalLocationArrayString = "[" + finalLoc.longitude() + "," + finalLoc.latitude() + "]";

        // Show route on the map
        getElement().executeJs("this.showRoute("+locationArrayString.toString()+", "+finalLocationArrayString+");");
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
     * @param latitude the latitude to fly to
     * @param longitude the longitude to fly to
     */
    public void flyTo(double latitude, double longitude) {
        getElement().executeJs("this.flyTo("+latitude+", "+longitude+");");
    }

    /**
     * Add a GeoJSON source to the map
     * @param id The identifier of the source
     * @param obj The GeoJSON object
     */
    public void addGeoJSON(String id, JsonObject obj) {
        getElement().executeJs("this.addGeoJSON('"+ id +"', "+obj.toString()+");");
    }

    /**
     * Add an extruded polygon layer to the map
     * @param id The identifier of the layer
     * @param source The GeoJSON source ID
     */
    public void addExtrusionLayer(String id, String source) {
        getElement().executeJs("this.addExtrudedLayer('"+ id +"', '"+ source +"');");
    }
}
