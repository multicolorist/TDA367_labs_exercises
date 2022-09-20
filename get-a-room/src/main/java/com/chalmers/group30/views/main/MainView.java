package com.chalmers.group30.views.main;

import com.chalmers.group30.controllers.DarkLightModeButtonController;
import com.chalmers.group30.controllers.FilterButtonController;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.views.components.buttons.DarkLightModeButton;
import com.chalmers.group30.views.components.buttons.FilterButton;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;

import java.io.IOException;
import java.util.List;

import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;

@PageTitle("GetARoom")
@Route(value = "")
public class MainView extends Main implements HasComponents, HasStyle {

    // UI
    DarkLightModeButtonController darkLightModeButtonController;
    DarkLightModeButton darkLightModeButton;
    FilterButtonController filterButtonController;
    FilterButton filterButton;

    private final ComponentRenderer<Component, Room> roomCardRenderer = new ComponentRenderer<>(room -> {
        HorizontalLayout cardLayout = new HorizontalLayout();
        cardLayout.setMargin(true);

        Avatar avatar = new Avatar(room.name(), "");
        avatar.setHeight("64px");
        avatar.setWidth("64px");

        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setSpacing(false);
        infoLayout.setPadding(false);
        infoLayout.getElement().appendChild(ElementFactory.createStrong(room.name()));
        infoLayout.add(new Div(new Text(room.building())));
        infoLayout.add(new Div(new Text("Latitude: " + room.location().latitude())));
        infoLayout.add(new Div(new Text("Longitude: " + room.location().longitude())));

        VerticalLayout detailsLayout = new VerticalLayout();
        detailsLayout.setSpacing(false);
        detailsLayout.setPadding(false);
        detailsLayout.add(new Div(new Text(room.uuid().toString())));
        infoLayout.add(new Details("Details", detailsLayout));

        cardLayout.add(avatar, infoLayout);
        return cardLayout;
    });


    @Autowired
    public MainView(
            DarkLightModeButtonController darkLightModeButtonController,
            DarkLightModeButton darkLightModeButton,
            FilterButtonController filterButtonController,
            FilterButton filterButton) throws IOException {

        // Init fields from dependency injection
        this.darkLightModeButtonController = darkLightModeButtonController;
        this.darkLightModeButton = darkLightModeButton;
        this.filterButtonController = filterButtonController;
        this.filterButton = filterButton;

        // Add additional styling for main view
        // TODO: Is this best-practice in Vaadin?
        addClassNames("main-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");

        // Get rooms
        // Data
        List<Room> rooms = darkLightModeButtonController.getRooms();

        // Init filter and dark/light mode button
        filterButton.addClickListener(filterButtonController.getListener());
        darkLightModeButton.addClickListener(darkLightModeButtonController.getListener());

        // Container
        VerticalLayout container = new VerticalLayout();
        container.setAlignItems(FlexComponent.Alignment.STRETCH);
        container.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Header
        H2 header = new H2("GetARoom");
        header.addClassNames("mb-0", "mt-xl", "text-3xl");
        HorizontalLayout headerContainer = new HorizontalLayout();
        headerContainer.setAlignItems(FlexComponent.Alignment.CENTER);
        headerContainer.setAlignSelf(FlexComponent.Alignment.END,
                this.filterButton,
                this.darkLightModeButton
        );
        headerContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerContainer.add(
                this.filterButton,
                header,
                this.darkLightModeButton
        );

        // Room card list
        VerticalLayout listContainer = new VerticalLayout();
        VirtualList<Room> roomCardsList = new VirtualList<>();
        roomCardsList.setItems(rooms);
        roomCardsList.setRenderer(roomCardRenderer);
        roomCardsList.setSizeFull();
        listContainer.add(roomCardsList);


        // Add to main VerticalLayout container - do we need this?
        container.add(headerContainer);

        // Add to main view
        add(
                container,
                roomCardsList
        );

        setSizeFull();
    }

}

