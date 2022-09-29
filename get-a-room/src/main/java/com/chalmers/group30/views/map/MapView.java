package com.chalmers.group30.views.map;

import com.chalmers.group30.views.components.MapLibreContainer;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Map")
@com.vaadin.flow.router.Route(value = "map")
public class MapView extends VerticalLayout {

    public MapView() {
        setSpacing(false);

        MapLibreContainer map = new MapLibreContainer();
        map.setSizeFull();
        add(map);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
