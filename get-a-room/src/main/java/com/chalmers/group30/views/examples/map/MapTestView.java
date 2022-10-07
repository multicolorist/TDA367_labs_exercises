package com.chalmers.group30.views.examples.map;

import com.chalmers.group30.controllers.MapViewController;
import com.chalmers.group30.models.ChalmersMapsAPI;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Route;
import com.chalmers.group30.models.utilities.WebRequests;
import com.chalmers.group30.views.components.MapView;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Map")
@com.vaadin.flow.router.Route(value = "map")
public class MapTestView extends VerticalLayout {

    public MapTestView() {
        setSpacing(false);

        MapView map = new MapView();
        MapViewController mapViewController = new MapViewController(map);
        map.setSizeFull();

        try {
            Route route =  Route.fromJSON(new ChalmersMapsAPI(new WebRequests()).route(new Location(57.696484034673915, 11.975264592149706), new Location(57.700295142972465, 11.965737593691228)));
            mapViewController.showRoute(route);
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
