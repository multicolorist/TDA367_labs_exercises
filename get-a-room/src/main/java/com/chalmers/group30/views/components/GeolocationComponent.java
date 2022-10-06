package com.chalmers.group30.views.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

import java.time.Instant;
import java.util.List;
import java.util.logging.Logger;

@Tag("geolocation")
@JsModule("")
public class GeolocationComponent extends Component {

    Logger logger = Logger.getLogger(GeolocationComponent.class.getName());

    private Double latitude;
    private Double longitude;
    private Instant lastUpdated;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public List<Double> updateLocation() {
        this.getElement().executeJs("this.updateLocation();");
        this.latitude = Double.valueOf(this.getElement().getProperty("latitude"));
        this.longitude = Double.valueOf(this.getElement().getProperty("longitude"));
        return List.of(latitude, longitude);
    }

    private void onUpdate(int latitude, int longitude) {
        this.latitude = (double) latitude;
        this.longitude = (double) longitude;
        this.lastUpdated = Instant.now();
    }

    private void onError(String message) {
        logger.log(java.util.logging.Level.WARNING, "Geolocation failed: "+message);
        latitude = Double.NaN;
        longitude = Double.NaN;
        this.lastUpdated = Instant.now();
    }
}
