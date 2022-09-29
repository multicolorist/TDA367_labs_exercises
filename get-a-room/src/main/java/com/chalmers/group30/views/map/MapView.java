package com.chalmers.group30.views.map;

import com.chalmers.group30.models.ChalmersMapsAPI;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.models.objects.Route;
import com.chalmers.group30.views.components.MapLibreContainer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

import java.util.UUID;

@PageTitle("Map")
@com.vaadin.flow.router.Route(value = "map")
public class MapView extends VerticalLayout {

    public MapView() {
        setSpacing(false);

        MapLibreContainer map = new MapLibreContainer();
        map.setSizeFull();

        try {
            map.addGeoJSON("buildings", new ChalmersMapsAPI().geoJsonBuildings());
            map.addGeoJSON("poi", new ChalmersMapsAPI().geoJsonPOI());
            map.addExtrusionLayer("buildings_extrude", "buildings");
            map.addExtrusionLayer("poi_extrude", "buildings");
        } catch (Exception e) {
            // Failed to add GeoJSON
        }
        try {
            Route r =  Route.fromJSON(new ChalmersMapsAPI().route(new Location(57.696484034673915, 11.975264592149706), new Location(57.700295142972465, 11.965737593691228)));
            map.showRoute(r);
        } catch (Exception e) {
            // Failed!
        }
        try {
            Room r = new Room("Svea 238", "", "", "", "", UUID.fromString("0067a767-c15f-4671-96dc-03792222d446"), new Location(57.706195, 11.936761), new Location(57.706195, 11.936761));
            map.addRoom(r);
        } catch (Exception e) {
            // Failure to add room
        }

        add(map);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
