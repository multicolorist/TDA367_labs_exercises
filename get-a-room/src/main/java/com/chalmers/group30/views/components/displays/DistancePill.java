package com.chalmers.group30.views.components.displays;

import com.vaadin.flow.component.html.Span;

/**
 * A component that contains a pill displaying a distance in meters
 */
public class DistancePill extends Span {
    public DistancePill(int distance) {
        Span distanceCounter = new Span(distance + "m");
            distanceCounter.getElement().getThemeList().add("badge pill small success");
            // distanceCounter.getStyle().set("margin-inline-start", "var(--lumo-space-s)");
            distanceCounter.getStyle().set("font-size", "var(--lumo-font-size-m)");
        // Accessibility
        String distanceCounterLabel = String.format("%d meters to the room from your location", distance);
            distanceCounter.getElement().setAttribute("aria-label", distanceCounterLabel);
            distanceCounter.getElement().setAttribute("title", distanceCounterLabel);
        add(distanceCounter);
    }
}
