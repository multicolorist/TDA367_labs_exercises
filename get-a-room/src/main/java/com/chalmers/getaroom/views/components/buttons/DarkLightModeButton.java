package com.chalmers.getaroom.views.components.buttons;

import com.chalmers.getaroom.views.utilities.HasChangeableIcon;
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
public class DarkLightModeButton extends Button implements HasChangeableIcon {
    private final Icon lightModeIcon = new Icon(VaadinIcon.SUN_O);

    @Autowired
    public DarkLightModeButton() {
        this.setIcon(lightModeIcon);
        this.getElement().setAttribute("aria-label", "Toggle dark/light mode");
    }

    @Override
    public void setIcon(Icon icon) {
        super.setIcon(icon);
    }
}
