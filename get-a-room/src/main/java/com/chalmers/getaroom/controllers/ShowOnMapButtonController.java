package com.chalmers.getaroom.controllers;

import com.chalmers.getaroom.models.GetARoomFacadeInterface;
import com.chalmers.getaroom.models.objects.Location;
import com.chalmers.getaroom.models.objects.Route;
import com.chalmers.getaroom.views.components.buttons.ShowOnMapButton;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the showOnMap button
 */
@Component
@UIScope
public class ShowOnMapButtonController {
    private final GetARoomFacadeInterface getARoomFacade;
    private final MapComponentController mapComponentController;
    private final GeolocationComponentController geolocationComponentController;
    private final Logger logger = Logger.getLogger(ShowOnMapButtonController.class.getName());

    public ShowOnMapButtonController(
            GetARoomFacadeInterface getARoomFacade,
            MapComponentController mapComponentController,
            GeolocationComponentController geolocationComponentController) {

        this.getARoomFacade = getARoomFacade;
        this.mapComponentController = mapComponentController;
        this.geolocationComponentController = geolocationComponentController;
    }

    /**
     * Gets a listener for the corresponding button
     *
     * @return Listener for the button
     */
    public ComponentEventListener<ClickEvent<Button>> getListener() {
        return new ShowOnMapButtonListener(getARoomFacade);
    }

    private class ShowOnMapButtonListener implements ComponentEventListener<ClickEvent<Button>> {
        private final GetARoomFacadeInterface getARoomFacade;

        public ShowOnMapButtonListener(GetARoomFacadeInterface getARoomFacade) {
            this.getARoomFacade = getARoomFacade;
        }

        @Override
        public void onComponentEvent(ClickEvent<Button> e) {
            ShowOnMapButton sourceButton = (ShowOnMapButton) e.getSource();
            Location destination = sourceButton.getRoom().location();
            Location userLocation;
            userLocation = geolocationComponentController.getLocation();
            if (Double.isNaN(userLocation.latitude())) {
                Notification.show("Please enable location services to see the route from your location to the room.");
                mapComponentController.showRoute(new Route(0, Arrays.asList(destination))); // Just mark the destination
            } else {
                try {
                    Route route = getARoomFacade.getWalkingRoute(userLocation, destination);
                    mapComponentController.showRoute(route);

                } catch (IOException ex) {
                    logger.log(Level.SEVERE, "Could not get walking route even though input was not NaN.", e);
                }
            }
            mapComponentController.flyTo(destination);
        }

    }
}
