package com.chalmers.group30.views.examples.geoloc;

import com.chalmers.group30.controllers.MapViewController;
import com.chalmers.group30.models.ChalmersMapsAPI;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Route;
import com.chalmers.group30.views.components.GeolocationComponent;
import com.chalmers.group30.views.components.MapView;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;

@PageTitle("Geolocation test")
@com.vaadin.flow.router.Route(value = "geoloc")
public class GeolocTestView extends VerticalLayout {

    public GeolocTestView() {
        setSpacing(false);

        GeolocationComponent geo = new GeolocationComponent();
        add(geo);

        Text text = new Text("Latitude: " + geo.getLatitude() + ", Longitude: " + geo.getLongitude());
        add(text);

        Button btn = new Button("Update location", e -> {
            geo.updateLocation();
            text.setText("Latitude: " + geo.getLatitude() + ", Longitude: " + geo.getLongitude());
        });

        add(btn);

        setSizeFull();
        setJustifyContentMode(JustifyContentMode.CENTER);
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        getStyle().set("text-align", "center");
    }

}
