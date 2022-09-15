package com.chalmers.group30.views.map;

import com.chalmers.group30.views.LeafletMap;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import software.xdev.vaadin.maps.leaflet.flow.data.LCenter;
import software.xdev.vaadin.maps.leaflet.flow.data.LTileLayer;

@PageTitle("About")
@Route(value = "about")
public class MapView extends VerticalLayout {

    public MapView() {
        setSpacing(false);

        LeafletMap map = new LeafletMap();
        map.setCenter(new LCenter(57.689798, 11.9736852));
        map.setZoom(17);
        map.setTileLayer(LTileLayer.DEFAULT_OPENSTREETMAP_TILE);
        map.setSizeFull();
        //map.setGeoJson();
        add(map);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
