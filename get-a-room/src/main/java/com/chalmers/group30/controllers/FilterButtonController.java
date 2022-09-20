package com.chalmers.group30.controllers;

import com.chalmers.group30.models.RoomServiceInterface;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// @Service
// @Scope(value = WebApplicationContext.SCOPE_APPLICATION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
@UIScope
public class FilterButtonController {
    private final RoomServiceInterface roomService;

    @Autowired
    public FilterButtonController(RoomServiceInterface roomService) {
        this.roomService = roomService;
    }

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
