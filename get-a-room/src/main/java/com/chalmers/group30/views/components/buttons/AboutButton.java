package com.chalmers.group30.views.components.buttons;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * A button for filtering rooms according to specific criteria
 */
@Component
@UIScope
public class AboutButton extends Button {
    Icon  lightModeIcon = new Icon(VaadinIcon.QUESTION);

    @Autowired
    public AboutButton() {
        this.setIcon(lightModeIcon);
    }
}