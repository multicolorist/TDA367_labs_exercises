package com.chalmers.group30.controllers;

import com.chalmers.group30.views.components.displays.AboutDialog;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Controller for the about button
 */
@Component
@UIScope
public class AboutButtonController {
    Dialog aboutDialog;

    @Autowired
    public AboutButtonController(AboutDialog aboutDialog) {
        this.aboutDialog = aboutDialog;
    }

    /**
     * Gets a listener for the corresponding button
     * @return Listener for the button
     */
    public ComponentEventListener<ClickEvent<Button>> getListener() {
        return new AboutButtonListener();
    }

    private class AboutButtonListener implements ComponentEventListener<ClickEvent<Button>> {
        @Override
        public void onComponentEvent(ClickEvent<Button> e) {
            aboutDialog.open();
        }
    }
}
