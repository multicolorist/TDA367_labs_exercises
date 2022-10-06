package com.chalmers.group30.controllers;

import com.chalmers.group30.views.components.buttons.ShowOnMapButton;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.stereotype.Component;

/**
 * Controller for the showOnMap button
 */
@Component
@UIScope
public class ShowOnMapButtonController {
    /**
     * Gets a listener for the corresponding button
     * @return The distance in meters between the position and the room
     */
    public static ComponentEventListener<ClickEvent<Button>> getListener() {
        return new ShowOnMapButtonListener();
    }

    private static class ShowOnMapButtonListener implements ComponentEventListener<ClickEvent<Button>> {
        @Override
        public void onComponentEvent(ClickEvent<Button> e) {
            ShowOnMapButton btn = (ShowOnMapButton) e.getSource();
            Notification.show("This makes the map slide up and marks UUID: " + btn.getUUID());
        }

    }
}
