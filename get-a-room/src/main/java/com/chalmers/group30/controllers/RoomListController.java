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
public class RoomListController {
    private final RoomServiceInterface roomService;

    @Autowired
    public RoomListController(RoomServiceInterface roomService) {
        this.roomService = roomService;
    }

    public List<Room> getRooms() throws IOException {
        return roomService.getRooms();
    }
}
