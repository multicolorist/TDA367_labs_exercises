package com.chalmers.group30.views.components.buttons;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A button for booking a room
 */
@Component
@UIScope
public class DarkLightModeButton extends Button {
    Icon lightModeIcon = new Icon(VaadinIcon.SUN_O);
    // Icon darkModeIcon = new Icon(VaadinIcon.MOON_O);

    @Autowired
    public DarkLightModeButton() {
        this.setIcon(lightModeIcon);
    }
}
