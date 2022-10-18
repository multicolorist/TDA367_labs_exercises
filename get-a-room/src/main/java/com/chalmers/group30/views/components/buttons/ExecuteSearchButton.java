package com.chalmers.group30.views.components.buttons;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.stereotype.Component;

/**
 * A button for booking a room
 */
@Component
@UIScope
public class ExecuteSearchButton extends Button {
    private final Icon icon = new Icon(VaadinIcon.SEARCH);

    public ExecuteSearchButton() {
        addClassNames(
                LumoUtility.Display.INLINE_FLEX,
                LumoUtility.BoxShadow.SMALL
        );
        this.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        this.setIcon(icon);
        this.setText("Search! ");
        // No aria-label needed, since the button has text
    }
}
