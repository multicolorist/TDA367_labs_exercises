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
/**
 * A custom component that uses the browser's geolocation API to get the user's location
 */
public class GeolocationComponent extends Component {

    private final Logger logger = Logger.getLogger(GeolocationComponent.class.getName());

    private Double latitude;
    private Double longitude;
    private Instant lastUpdated;

    /**
     * Gets the last latitude of the user
     * @return The latitude of the user
     */
    public Double getLatitude() {
        this.getElement().executeJs("return this.getLatitude();").then(Double.class, result -> {
            this.latitude = (result == null) ? Double.NaN : result;
        });
        return this.latitude;
    }

    /**
     * Gets the last longitude of the user
     * @return The longitude of the user
     */
    public Double getLongitude() {
        this.getElement().executeJs("return this.getLongitude();").then(Double.class, result -> {
            this.longitude = (result == null) ? Double.NaN : result;
        });
        return this.longitude;
    }

    /**
     * Attempts to get the user's location
     */
    public void updateLocation() {
        this.getElement().executeJs("this.updateLocation();");
    }

    @ClientCallable
    private void onUpdate() {
        getLatitude();
        getLongitude();
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
