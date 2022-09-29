package com.chalmers.group30.views.components;

import com.chalmers.group30.models.ChalmersMapsAPI;
import com.chalmers.group30.models.objects.Location;
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
//@StyleSheet("https://unpkg.com/maplibre-gl@2.4.0/dist/maplibre-gl.css")
public class MapLibreContainer extends Component implements HasSize, HasStyle, HasComponents {

    public void addRoute(Route r) {
        // Generate JS object of locations
        StringBuilder locationArrayString = new StringBuilder("{\"type\": \"FeatureCollection\",\"features\":[{\"type\": \"Feature\",\"properties\": {},\"geometry\": {\"type\": \"LineString\",\"coordinates\": [");
        for(Location point : r.maneuvers()){
            locationArrayString.append("[").append(point.longitude()).append(",").append(point.latitude()).append("],");
        }
        // Remove final stray comma
        locationArrayString.deleteCharAt(locationArrayString.length()-1);

        // Add a point representing the route destination
        Location finalLoc = r.maneuvers().get(r.maneuvers().size() - 1);
        locationArrayString.append("]}}, {\"type\": \"Feature\",\"geometry\": {\"type\": \"Point\",\"coordinates\": [")
                .append(finalLoc.longitude())
                .append(",").append(finalLoc.latitude())
                .append("]},\"properties\": {}}]}");


        UUID uuid = UUID.randomUUID();

        addGeoJSON("route-"+uuid, locationArrayString.toString());
        getElement().executeJs("this.map.addLayer({'id': 'route-layer-"+uuid+"','type': 'line','source': 'route-"+uuid+"','layout': {'line-join': 'round','line-cap': 'round'},'paint': {'line-color': '#314ccd','line-width': 8}});");
        getElement().executeJs("this.map.addLayer({'id': 'route-dest-layer-"+uuid+"-end','type': 'circle','source': 'route-"+uuid+"','paint': {'circle-radius': 10,'circle-color': '#f4347c'}, 'filter': ['==', '$type', 'Point']});");
    }

    public void addGeoJSON(String id, JsonObject obj) {
        getElement().executeJs("var geojson = "+obj.toString()+"; this.addGeoJSON('"+ id +"', geojson);");
    }

    public void addGeoJSON(String id, String obj) {
        getElement().executeJs("var geojson = "+obj+"; this.addGeoJSON('"+ id +"', geojson);");
    }

    public void addExtrusionLayer(String id, String source) {
        getElement().executeJs("this.addExtrudedLayer('"+ id +"', '"+ source +"');");
    }

    @ClientCallable
    private void onReady() {
        try {
            addGeoJSON("buildings", new ChalmersMapsAPI().geoJsonBuildings());
            addGeoJSON("poi", new ChalmersMapsAPI().geoJsonPOI());
            addExtrusionLayer("buildings_extrude", "buildings");
            addExtrusionLayer("poi_extrude", "buildings");
        } catch (Exception e) {
            // Failed to add GeoJSON
        }
        try {
            Route r =  Route.fromJSON(new ChalmersMapsAPI().route(new Location(57.696484034673915, 11.975264592149706), new Location(57.700295142972465, 11.965737593691228)));
            addRoute(r);
        } catch (Exception e) {
            // Failed!
        }
        //getElement().executeJs("this.map.showCollisionBoxes = true;");
    }
}
