package com.chalmers.group30.views;

/**
 * Mediator that decouples communicating objects
 */
public interface Mediator {
    /**
     * Notifies the mediator of an event
     * @param event The event that occurred
     */
    void notify(String event);
}
