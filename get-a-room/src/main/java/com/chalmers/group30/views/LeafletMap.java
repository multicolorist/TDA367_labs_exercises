package com.chalmers.group30.views;

import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Route;
import com.google.gson.JsonObject;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.JavaScript;
import software.xdev.vaadin.maps.leaflet.flow.LMap;

@JavaScript("https://opengeo.tech/maps/leaflet-gps/dist/leaflet-gps.min.js")
public class LeafletMap extends LMap {
    public LeafletMap() {
        super();
        this.getElement().executeJs("this.map.addControl( new L.Control.Gps({autoActive:true, setView:true}) );");
    }
    public void setGeoJson(JsonObject obj) {
        this.getElement().executeJs("L.geoJSON(" + obj.toString() + ").addTo(this.map);");
    }

    public void drawRoute(Route r) {
        // Generate JS array of locations
        StringBuilder locationArrayString = new StringBuilder("[");
        for(Location point : r.maneuvers()){
            locationArrayString.append("[").append(point.latitude()).append(",").append(point.longitude()).append("],");
        }
        // Remove final stray comma
        locationArrayString.deleteCharAt(locationArrayString.length()-1);
        locationArrayString.append("]");

        // Add a line representing the route
        this.getElement().executeJs("L.polyline(" + locationArrayString.toString() + ", {color: 'blue'}).addTo(this.map);");

        // Display destination marker
        Location finalLoc = r.maneuvers().get(r.maneuvers().size() - 1);
        this.getElement().executeJs("L.marker(["+finalLoc.latitude()+", "+finalLoc.longitude()+"]).addTo(this.map);");
    }
}
