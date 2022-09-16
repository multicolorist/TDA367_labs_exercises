package com.chalmers.group30.views.map;

import com.chalmers.group30.models.ChalmersMapsAPI;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Route;
import com.chalmers.group30.views.LeafletMap;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import software.xdev.vaadin.maps.leaflet.flow.data.LCenter;
import software.xdev.vaadin.maps.leaflet.flow.data.LTileLayer;

@PageTitle("Map")
@com.vaadin.flow.router.Route(value = "map")
public class MapView extends VerticalLayout {

    public MapView() {
        setSpacing(false);

        LeafletMap map = new LeafletMap();
        map.setCenter(new LCenter(57.689798, 11.9736852));
        map.setZoom(17);
        map.setTileLayer(LTileLayer.DEFAULT_OPENSTREETMAP_TILE);
        map.setSizeFull();
        try{
            map.setGeoJson(new ChalmersMapsAPI().geoJson());
        } catch(Exception e) {
            // Failed to add GeoJSON
        }

        try {
            Route r =  Route.fromJSON(new ChalmersMapsAPI().route(new Location(57.696484034673915, 11.975264592149706), new Location(57.700295142972465, 11.965737593691228)));
            map.drawRoute(r);
        } catch (Exception e) {
            // Failed!
        }


        add(map);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
