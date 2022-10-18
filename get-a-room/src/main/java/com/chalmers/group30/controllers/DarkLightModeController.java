package com.chalmers.group30.controllers;

import com.chalmers.group30.views.HasChangeableIcon;
import com.chalmers.group30.views.components.PreferredClientThemeComponent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.chalmers.group30.views.utilities.JavaScriptDOMUtils.setDOMTagAttribute;

/**
 * Controller for darkmode<->lightmode
 */
@org.springframework.stereotype.Component
@UIScope
@Tag("dark-light-mode-controller")
public class DarkLightModeController implements ComponentEventListener<ClickEvent<Button>> {

    private final Icon lightModeIcon = new Icon(VaadinIcon.SUN_O);
    private final Icon darkModeIcon = new Icon(VaadinIcon.MOON_O);
    private final ThemeList themeList = UI.getCurrent().getElement().getThemeList();

    private final PreferredClientThemeComponent preferredClientThemeComponent;
    private final List<HasChangeableIcon> iconsToChange = new ArrayList<>();

    @Autowired
    public DarkLightModeController(PreferredClientThemeComponent preferredClientThemeComponent) {
        this.preferredClientThemeComponent = preferredClientThemeComponent;
    }

    /**
     * Applies the darkmode or lightmode theme depending on the user's preference.
     * If the user does not hava a theme cookie (set when the click listener is triggered), then the clients browser theme is used.
     */
    public void applyClientPreferredTheme() {
        Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();

        Optional<Cookie> themeCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("theme"))
                .findFirst();

        if (themeCookie.isPresent()) {
            if (themeCookie.get().getValue().equals("dark")) {
                setTheme(true, true);
            } else if (themeCookie.get().getValue().equals("light")) {
                setTheme(false, true);
            } else {
                setClientPreferredTheme();
            }
        } else {
            setClientPreferredTheme();
        }
    }


    /**
     * Registers a component that has an icon that should be changed when the theme is changed
     *
     * @param hasChangeableIcon The component to register
     */
    public void registerIconToChange(HasChangeableIcon hasChangeableIcon) {
        iconsToChange.add(hasChangeableIcon);
    }

    /**
     * Changes all registered changeable icons
     *
     * @param icon The icon to change to
     */
    private void changeIcons(Icon icon) {
        for (HasChangeableIcon changeableIcon : iconsToChange) {
            changeableIcon.setIcon(icon);
        }
    }

    private void setTheme(boolean useDarkTheme, boolean saveToCookie) {

        if (saveToCookie) {
            Cookie themeCookie = new Cookie("theme", useDarkTheme ? "dark" : "light");
            themeCookie.setPath("/");
            themeCookie.setMaxAge(60 * 60 * 24 * 365);
            VaadinService.getCurrentResponse().addCookie(themeCookie);
        }

        if (useDarkTheme) {
            // Modify html tag
            setDOMTagAttribute("html", "theme", "dark");

            // Modify body tag
            themeList.remove(Lumo.LIGHT);
            themeList.add(Lumo.DARK);

            // Update icon
            changeIcons(darkModeIcon);
        } else {
            // Modify html tag
            setDOMTagAttribute("html", "theme", "light");

            // Modify body tag
            themeList.remove(Lumo.DARK);
            themeList.add(Lumo.LIGHT);

            // Update icon
            changeIcons(lightModeIcon);
        }
    }

    /**
     * Sets the theme to the clients browser's preferred theme
     */
    private void setClientPreferredTheme() {
        preferredClientThemeComponent.getClientPrefersDarkModeJavaScriptResult().then(Boolean.class, prefersDark -> {
            setTheme(prefersDark, false);
        });
    }

    /**
     * Gets a listener for the theme button
     *
     * @return A click event listener
     */
    public ComponentEventListener<ClickEvent<Button>> getListener() {
        return this;
    }

    /**
     * Click event listener for a theme button
     *
     * @param e The click event
     */
    @Override
    public void onComponentEvent(ClickEvent<Button> e) {
        setTheme(!themeList.contains(Lumo.DARK), true);
    }

}
