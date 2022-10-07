package com.chalmers.group30.controllers;

import com.chalmers.group30.models.GetARoomFacade;
import com.chalmers.group30.models.GetARoomFacadeInterface;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Route;
import com.chalmers.group30.views.components.buttons.ShowOnMapButton;
import com.chalmers.group30.views.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Controller for the showOnMap button
 */
@Component
@UIScope
public class ShowOnMapButtonController {
    GetARoomFacadeInterface getARoomFacade;
    MainView mainView;
    MapViewController mapViewController;
    // @Autowired
    public ShowOnMapButtonController(
            GetARoomFacadeInterface getARoomFacade,
            MainView mainView,
            MapViewController mapViewController) {

        this.getARoomFacade = getARoomFacade;
        this.mainView = mainView;
        this.mapViewController = mapViewController;
    }

    /**
     * Gets a listener for the corresponding button
     * @return Listener for the button
     */
    public ComponentEventListener<ClickEvent<Button>> getListener() {
        return new ShowOnMapButtonListener(getARoomFacade, mainView);
    }

    private class ShowOnMapButtonListener implements ComponentEventListener<ClickEvent<Button>> {
        GetARoomFacadeInterface getARoomFacade;
        MainView mainView;
        public ShowOnMapButtonListener(GetARoomFacadeInterface getARoomFacade, MainView mainView) {
            this.getARoomFacade = getARoomFacade;
            this.mainView = mainView;
        }

        @Override
        public void onComponentEvent(ClickEvent<Button> e) {
            ShowOnMapButton sourceButton = (ShowOnMapButton) e.getSource();
            Notification.show("This makes the map slide up and marks UUID: " + sourceButton.getRoom().uuid());
            // TODO: Change to real user loc when available
            Location userLocation = new Location(57.708870, 11.974560);
            Location destination = sourceButton.getRoom().location();
            try {
                Route route = getARoomFacade.getWalkingRoute(userLocation, destination);
                mapViewController.showRoute(route);
                mapViewController.flyTo(destination);
                mainView.setDrawerOpened(true);
            } catch (IOException ex) {
                Notification.show("Could not get walking route");
                //TODO: Add logging
            }
        }

    }
}
