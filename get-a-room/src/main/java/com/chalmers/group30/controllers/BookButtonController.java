package com.chalmers.group30.controllers;

import com.chalmers.group30.views.components.buttons.BookButton;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;

/**
 * Controller for the book button
 */
public class BookButtonController{
    /**
     * Gets a listener for the corresponding button
     * @return Listener for the button
     */
    public static ComponentEventListener<ClickEvent<Button>> getListener() {
        return new BookButtonListener();
    }

    private static class BookButtonListener implements ComponentEventListener<ClickEvent<Button>> {
        @Override
        public void onComponentEvent(ClickEvent<Button> e) {
            BookButton btn = (BookButton) e.getSource();
            Notification.show("This leads to time-edit for UUID: " + btn.getUUID());
        }
    }
}
