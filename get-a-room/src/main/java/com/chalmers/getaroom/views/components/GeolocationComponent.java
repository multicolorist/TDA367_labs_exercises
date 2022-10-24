package com.chalmers.getaroom.views.components;

import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.spring.annotation.UIScope;

/**
 * A custom component that uses the browser's geolocation API to get the user's location
 */
@Tag("geoloc-custom")
@JsModule("./geolocation/geolocation.ts")
@org.springframework.stereotype.Component
@UIScope
public class GeolocationComponent extends Component {
    private Double latitude = Double.NaN;
    private Double longitude = Double.NaN;

    /**
     * Gets the last latitude of the user
     *
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
     *
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
    }

    @ClientCallable
    private void onError() {
        latitude = Double.NaN;
        longitude = Double.NaN;
    }
}
