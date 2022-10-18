package com.chalmers.group30.views.components.buttons;

import com.chalmers.group30.models.objects.Room;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * A button for showing a specific room on the map
 */
public class ShowOnMapButton extends Button {
    private final Icon icon = new Icon(VaadinIcon.MAP_MARKER);
    private Room room;

    public ShowOnMapButton(Room room) {
        this.room = room;
        addClassNames(
                LumoUtility.Display.INLINE_FLEX,
                LumoUtility.BoxShadow.SMALL
        );
        addThemeVariants(ButtonVariant.LUMO_SMALL);
        this.setIcon(icon);
        this.setText("Show on map");
        // No aria-label needed, since the button has text
    }

    public Room getRoom() {
        return this.room;
    }
}
