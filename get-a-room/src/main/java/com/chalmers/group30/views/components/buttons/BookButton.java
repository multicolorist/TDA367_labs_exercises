package com.chalmers.group30.views.components.buttons;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.UUID;

/**
 * A button for booking a room
 */
public class BookButton extends Button implements ButtonHasUUID {
    UUID uuid;
    Icon icon = new Icon(VaadinIcon.CALENDAR_CLOCK);

    public BookButton(UUID uuid) {
        addClassNames(
                LumoUtility.Display.INLINE_FLEX,
                LumoUtility.BoxShadow.SMALL
        );
        addThemeVariants(ButtonVariant.LUMO_SMALL);
        this.uuid = uuid;
        this.setIcon(icon);
        this.setText("Book");
    }

    @Override
    public UUID getUUID() {
        return this.uuid;
    }
}
