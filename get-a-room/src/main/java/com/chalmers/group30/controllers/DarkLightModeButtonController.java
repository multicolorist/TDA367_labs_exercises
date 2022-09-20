package com.chalmers.group30.controllers;

import com.chalmers.group30.models.RoomServiceInterface;
import com.chalmers.group30.models.objects.Room;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@UIScope
public class DarkLightModeButtonController {
    private final RoomServiceInterface roomService;

    @Autowired
    public DarkLightModeButtonController(RoomServiceInterface roomService) {
        this.roomService = roomService;
    }

    public List<Room> getRooms() throws IOException {
        return roomService.getRooms();
    }

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
                themeList.remove(Lumo.DARK);
                e.getSource().setIcon(lightModeIcon);
            } else {
                themeList.add(Lumo.DARK);
                e.getSource().setIcon(darkModeIcon);
            }
        }

    }
}
