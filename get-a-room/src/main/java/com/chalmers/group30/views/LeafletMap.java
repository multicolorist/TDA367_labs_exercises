package com.chalmers.group30.views;

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
}
