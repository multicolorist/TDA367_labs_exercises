package com.chalmers.group30.controllers;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller for the filter button to filter rooms according to specific criteria
 */
@Component
@UIScope
public class AboutButtonController {
    // TODO: Add filtering logic

    @Autowired
    public AboutButtonController() {

    }

    /**
     * Gets a listener for the corresponding button
     * @return The distance in meters between the position and the room
     */
    public ComponentEventListener<ClickEvent<Button>> getListener() {
        return new FilterButtonListener();
    }

    private static class FilterButtonListener implements ComponentEventListener<ClickEvent<Button>> {
        @Override
        public void onComponentEvent(ClickEvent<Button> e) {
            Notification.show("You've just filtered the rooms! Almost.");
        }

    }
}
