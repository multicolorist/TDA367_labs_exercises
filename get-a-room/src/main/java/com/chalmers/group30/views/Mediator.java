package com.chalmers.group30.views;

/**
 * Mediator that decouples communicating objects
 */
public interface Mediator {
    void notify(String event);
}
