package com.chalmers.group30.controllers;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.stereotype.Component;

import static com.chalmers.group30.views.utilities.JavascriptUtils.setDOMTagAttribute;

/**
 * Controller for the darkmode<->lightmode button
 */
@Component
@UIScope
public class DarkLightModeButtonController {
    /**
     * Gets a listener for the corresponding button
     * @return The distance in meters between the position and the room
     */
    public ComponentEventListener<ClickEvent<Button>> getListener() {
        return new DarkLightModeButtonListener();
    }

    private static class DarkLightModeButtonListener implements ComponentEventListener<ClickEvent<Button>> {
        private final Icon lightModeIcon = new Icon(VaadinIcon.SUN_O);
        private final Icon darkModeIcon = new Icon(VaadinIcon.MOON_O);
        private final ThemeList themeList = UI.getCurrent().getElement().getThemeList();

        @Override
        public void onComponentEvent(ClickEvent<Button> e) {
            if (themeList.contains(Lumo.DARK)) {
                // Modify html tag
                setDOMTagAttribute("html", "theme", "light");

                // Modify body tag
                themeList.remove(Lumo.DARK);
                themeList.add(Lumo.LIGHT);

                // Update icon
                e.getSource().setIcon(lightModeIcon);
            } else {
                // Modify html tag
                setDOMTagAttribute("html", "theme", "dark");

                // Modify body tag
                themeList.remove(Lumo.LIGHT);
                themeList.add(Lumo.DARK);

                // Update icon
                e.getSource().setIcon(darkModeIcon);
            }
        }

    }
}
