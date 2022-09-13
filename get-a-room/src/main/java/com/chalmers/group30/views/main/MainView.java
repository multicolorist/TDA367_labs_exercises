package com.chalmers.group30.views.main;

import com.chalmers.group30.models.RoomService;
import com.chalmers.group30.models.objects.Room;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.vaadin.flow.dom.ThemeList;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.theme.lumo.Lumo;

@PageTitle("GetARoom")
@Route(value = "")
public class MainView extends Main implements HasComponents, HasStyle {

    private List<Room> rooms;


    private ComponentRenderer<Component, Room> roomCardRenderer = new ComponentRenderer<>(room -> {
        HorizontalLayout cardLayout = new HorizontalLayout();
        cardLayout.setMargin(true);

        Avatar avatar = new Avatar(room.getName(), "");
        avatar.setHeight("64px");
        avatar.setWidth("64px");

        VerticalLayout infoLayout = new VerticalLayout();
        infoLayout.setSpacing(false);
        infoLayout.setPadding(false);
        infoLayout.getElement().appendChild(ElementFactory.createStrong(room.getName()));
        infoLayout.add(new Div(new Text(room.getBuilding())));
        infoLayout.add(new Div(new Text("Latitude: " + room.getLatitude())));
        infoLayout.add(new Div(new Text("Longitude: " + room.getLongitude())));

        VerticalLayout detailsLayout = new VerticalLayout();
        detailsLayout.setSpacing(false);
        detailsLayout.setPadding(false);
        detailsLayout.add(new Div(new Text(room.getUid())));
        infoLayout.add(new Details("Details", detailsLayout));

        cardLayout.add(avatar, infoLayout);
        return cardLayout;
    });


    Button createDarkLightModeButton() {
        Button darkLightModeButton = new Button(new Icon(VaadinIcon.SUN_O));
        Icon lightModeIcon = new Icon(VaadinIcon.SUN_O);
        Icon darkModeIcon = new Icon(VaadinIcon.MOON_O);
        ThemeList themeList = UI.getCurrent().getElement().getThemeList();

        darkLightModeButton.addClickListener(e -> {
            if (themeList.contains(Lumo.DARK)) {
                themeList.remove(Lumo.DARK);
                darkLightModeButton.setIcon(lightModeIcon);
            } else {
                themeList.add(Lumo.DARK);
                darkLightModeButton.setIcon(darkModeIcon);
            }
        });
        darkLightModeButton.setIcon(lightModeIcon);

        return darkLightModeButton;
    }

    Button createFilterButton() {
        Button filterButton = new Button(new Icon(VaadinIcon.FILTER));

        filterButton.addClickListener(e -> {
            Notification.show("You've just filtered the rooms! Almost.");
        });

        return filterButton;
    }


    public MainView() throws IOException {
        // Add styling for main view
        // TODO: Is this best-practice in Vaadin?
        addClassNames("main-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");

        // Get rooms
        this.rooms = RoomService.getRooms();

        // Init filter and dark/light mode button
        Button filterButton = createFilterButton();
        Button darkLightModeButton = createDarkLightModeButton();

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
                filterButton,
                darkLightModeButton
        );
        headerContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerContainer.add(
                filterButton,
                header,
                darkLightModeButton
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

