package com.chalmers.group30.views;

import com.google.gson.JsonObject;
import com.vaadin.flow.component.Component;
import software.xdev.vaadin.maps.leaflet.flow.LMap;

public class LeafletMap extends LMap {
    public void setGeoJson(JsonObject obj) {
        String setObj = "var geoJsonString = " + obj.toString() + ";";
        this.getElement().executeJs(setObj + "L.geoJSON(geoJsonString).addTo(this.map);");
    }

}
