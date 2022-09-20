package com.chalmers.group30.views.components.buttons;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@UIScope
public class FilterButton extends Button {
    Icon lightModeIcon = new Icon(VaadinIcon.FILTER);

    @Autowired
    public FilterButton() {
        this.setIcon(lightModeIcon);
    }
}
