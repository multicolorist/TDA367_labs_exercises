package com.chalmers.group30.views.components;

import com.chalmers.group30.models.ChalmersMapsAPI;
import com.google.gson.JsonObject;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.dependency.CssImport.Container;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.StyleSheet;
import com.vaadin.flow.component.html.Div;

import java.io.Serializable;
import java.util.ArrayList;

@Tag("maplibre-gl-js")
@JsModule("./maplibre/maplibre.ts")
//@StyleSheet("https://unpkg.com/maplibre-gl@2.4.0/dist/maplibre-gl.css")
public class MapLibreContainer extends Component implements HasSize, HasStyle, HasComponents {

    public MapLibreContainer() {
        getElement().executeJs("this.map.showCollisionBoxes = true;");
    }

    public void addGeoJSON(String id, JsonObject obj) {
        getElement().executeJs("var geojson = "+obj.toString()+"; addGeoJSON("+ id +", geojson);");
    }

    public void addExtrusionLayer(String id, String source) {
        getElement().executeJs("addExtrudedLayer("+ id +"', '"+ source +"');");
    }

    @ClientCallable
    public void onReady() {
        System.out.println("MapLibre is ready!");
        try {
            addGeoJSON("buildings", new ChalmersMapsAPI().geoJson());
            addExtrusionLayer("buildings_extrude", "buildings");
        } catch (Exception e) {
            // Failed to add GeoJSON
        }
    }
}
