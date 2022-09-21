package com.chalmers.group30.views.components.buttons;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import java.util.UUID;

/**
 * A button for showing a specific room on the map
 */
public class ShowOnMapButton extends Button implements ButtonHasUUID {
    UUID uuid;
    Icon icon = new Icon(VaadinIcon.MAP_MARKER);

    public ShowOnMapButton(UUID uuid) {
        this.uuid = uuid;
        this.setIcon(icon);
        this.setText("Show on map");
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }
}
