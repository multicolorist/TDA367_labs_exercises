package com.chalmers.group30.views.components;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Tag("geoloc-custom")
@JsModule("./geolocation/geolocation.ts")
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
        this.latitude = Double.valueOf(Optional.ofNullable(this.getElement().getProperty("latitude")).orElse("0"));
        this.longitude = Double.valueOf(Optional.ofNullable(this.getElement().getProperty("longitude")).orElse("0"));
        return List.of(latitude, longitude);
    }

    @ClientCallable
    private void onUpdate() {
        this.latitude = Double.valueOf(Optional.ofNullable(this.getElement().getProperty("latitude")).orElse("0"));
        this.longitude = Double.valueOf(Optional.ofNullable(this.getElement().getProperty("longitude")).orElse("0"));
        this.lastUpdated = Instant.now();
    }

    @ClientCallable
    private void onError() {
        logger.log(java.util.logging.Level.WARNING, "Geolocation failed");
        latitude = Double.NaN;
        longitude = Double.NaN;
        this.lastUpdated = Instant.now();
    }
}
