package com.chalmers.group30.views.main;

import com.chalmers.group30.controllers.*;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.views.components.buttons.BookButton;
import com.chalmers.group30.views.components.buttons.DarkLightModeButton;
import com.chalmers.group30.views.components.buttons.FilterButton;
import com.chalmers.group30.views.components.buttons.ShowOnMapButton;
import com.chalmers.group30.views.components.controls.DateTimeControls;
import com.chalmers.group30.views.components.controls.IntegerUnlabeledStepper;
import com.chalmers.group30.views.components.displays.DistancePill;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
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
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;


// Fix for Vaadin bug-ish that detail summary does not expand
// see for ex. https://vaadin.com/forum/thread/18151243/expanding-details-component
@CssImport(value = "./themes/getaroom/componentSpecific/vaadin-details.css", themeFor = "vaadin-details")

@PageTitle("GetARoom")
@Route(value = "")
// public class MainView extends Main implements HasComponents, HasStyle {
public class MainView extends AppLayout implements HasComponents, HasStyle {

    // UI
    DarkLightModeButtonController darkLightModeButtonController;
    DarkLightModeButton darkLightModeButton;
    FilterButtonController filterButtonController;
    FilterButton filterButton;
    RoomListController roomListController;

    // Class rendering the room list
    private final ComponentRenderer<Component, Room> roomListEntryRenderer = new ComponentRenderer<>(room -> {
        // Buttons
        Button bookButton = new BookButton(room.uuid());
        bookButton.addClickListener(BookButtonController.getListener());
        Button showMapButton = new ShowOnMapButton(room.uuid());
        showMapButton.addClickListener(ShowOnMapButtonController.getListener());

        // Layouts
        HorizontalLayout listEntryLayout = new HorizontalLayout();
        listEntryLayout.addClassNames(
                // LumoUtility.Display.BLOCK
                LumoUtility.Width.FULL
        );

        // Image, avatar or distance to display to the left in the card
        Location userLocation = new Location(57.696484034673915, 11.975264592149706); // TODO: Change this to the user's location from a controller
        int distanceToRoom = (int) Math.round(roomListController.getBirdsDistance(userLocation, room.location()));// TODO: Change this to the user's location from a controller
        DistancePill distanceIndicator = new DistancePill(distanceToRoom);
        // Avatar distanceIndicator = new Avatar(distanceToRoom + " meters to this room from your location");
        // distanceIndicator.setAbbreviation(distanceToRoom + "m");
        // distanceIndicator.setHeight("64px");
        // distanceIndicator.setWidth("64px");
        distanceIndicator.addClassNames(
                LumoUtility.AlignSelf.CENTER
        );

        // Top left part of the card, seen both folded and unfolded
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.addClassNames(
                LumoUtility.Width.FULL
                );

        VerticalLayout topLayoutLeft = new VerticalLayout();
        topLayoutLeft.getElement().appendChild(ElementFactory.createStrong("Until X"));
        topLayoutLeft.add(new Div(new Text(room.name())));
        //topLayoutLeft.add(new Div(new Text(distanceToRoom + " meters away")));

        // VerticalLayout topSpacer = new VerticalLayout();

        // Top right part of the card, seen both folded and unfolded
        VerticalLayout topLayoutRight = new VerticalLayout();
        topLayoutRight.add(new Div(new Text(room.building())));
        topLayoutRight.add(new Div(new Text("Floor " + room.floor())));

        // topLayout.add(topLayoutLeft, topSpacer, topLayoutRight);
        topLayout.add(topLayoutLeft, topLayoutRight);

        // Bottom part of the card, seen only when unfolded
        VerticalLayout bottomLayout = new VerticalLayout();
        // HorizontalLayout textRow = new HorizontalLayout();
        // textRow.addClassNames(
        //         LumoUtility.AlignSelf.CENTER
        // );
        // textRow.add(new Div(new Text(room.streetAddress())));
        HorizontalLayout buttonRow = new HorizontalLayout(); // Lower row for buttons

        buttonRow.add(showMapButton, bookButton);
        buttonRow.addClassNames(
                // LumoUtility.Display.FLEX,
                LumoUtility.AlignSelf.CENTER
        );
        // bottomLayout.add(textRow,buttonRow);
        bottomLayout.add(buttonRow);

        // Add the content to the card
        listEntryLayout.add(distanceIndicator, topLayout);

        // Make a foldable panel of the entry
        // https://vaadin.com/api/platform/23.2.1/com/vaadin/flow/component/accordion/AccordionPanel.html or
        // https://vaadin.com/docs/latest/components/details
        // TODO: Figure out how to add shadow DOM CSS of summary-content part to width: 100%
        Details foldablePanel = new Details(listEntryLayout, bottomLayout);
        // foldablePanel.addThemeVariants(DetailsVariant.FILLED);
        foldablePanel.addClassNames(
                "custom-full-width",
                LumoUtility.Padding.MEDIUM,
                LumoUtility.BoxShadow.SMALL // https://vaadin.com/docs/latest/styling/lumo/utility-classes/#box-shadow
        );
        return foldablePanel;
        // return listEntryLayout;
    });


    @Autowired
    public MainView(
            DarkLightModeButtonController darkLightModeButtonController,
            DarkLightModeButton darkLightModeButton,
            FilterButtonController filterButtonController,
            FilterButton filterButton,
            RoomListController roomListController) throws IOException {

        // Init fields from dependency injection
        this.darkLightModeButtonController = darkLightModeButtonController;
        this.darkLightModeButton = darkLightModeButton;
        this.filterButtonController = filterButtonController;
        this.filterButton = filterButton;
        this.roomListController = roomListController;

        // Add additional styling for main view
        // TODO: Is this best-practice in Vaadin?
        // addClassNames("main-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");
        addClassNames(
                // "main-view",
                // LumoUtility.MaxWidth.SCREEN_XLARGE,
                LumoUtility.Margin.Horizontal.AUTO,
                LumoUtility.Padding.Bottom.LARGE,
                LumoUtility.Padding.Horizontal.LARGE
        );

        // Get rooms
        // Data
        List<Room> rooms = roomListController.getRooms();

        // Init filter and dark/light mode button
        filterButton.addClickListener(filterButtonController.getListener());
        darkLightModeButton.addClickListener(darkLightModeButtonController.getListener());

        // Container
        // VerticalLayout container = new VerticalLayout();
        // container.setAlignItems(FlexComponent.Alignment.STRETCH);
        // container.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);

        // Header
        H2 title = new H2("GetARoom");
        title.addClassNames(
                LumoUtility.Margin.Bottom.SMALL,
                LumoUtility.Margin.Top.SMALL,
                LumoUtility.FontSize.XXXLARGE
        );
        HorizontalLayout headerContainer = new HorizontalLayout();
        headerContainer.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.AlignSelf.CENTER,
                LumoUtility.Background.BASE
        );
        headerContainer.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
        headerContainer.add(
                this.filterButton,
                title,
                this.darkLightModeButton
        );

        // Query container
        Div queryContainer = new Div();
        queryContainer.addClassNames(
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.AlignSelf.CENTER,
                LumoUtility.Background.BASE,
                LumoUtility.FontSize.LARGE,
                LumoUtility.FontWeight.SEMIBOLD
        );
        HorizontalLayout roomQueryRow = new HorizontalLayout();
        roomQueryRow.add(
                new Div(new Text("Rooms for ")),
                new IntegerUnlabeledStepper(1, 12, 4),
                new Div(new Text(" people from "))
        );
        roomQueryRow.addClassNames(
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.AlignSelf.CENTER,
                LumoUtility.Background.BASE,
                LumoUtility.FontSize.LARGE,
                LumoUtility.FontWeight.SEMIBOLD
        );

        queryContainer.add(
                roomQueryRow,
                new DateTimeControls()
        );

        // Navbar
        VerticalLayout navbarContainer = new VerticalLayout(); // To keep elements vertically ordered
        navbarContainer.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.JustifyContent.BETWEEN, // horizontal
                LumoUtility.AlignItems.CENTER, // vertical
                LumoUtility.AlignSelf.CENTER,  // vertical
                LumoUtility.Background.BASE
        );
        navbarContainer.add(headerContainer, queryContainer);

        addToNavbar(navbarContainer);


        // Room list
        VirtualList<Room> roomList = new VirtualList<>();
        roomList.addClassNames(
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.Padding.Top.MEDIUM,
                LumoUtility.Padding.Bottom.XLARGE
        );
        roomList.setItems(rooms);
        roomList.setRenderer(roomListEntryRenderer);
        roomList.setHeight("85%");


        // Main view composition (header in navbar which is already a child)
        add(
                roomList
        );

        // setSizeFull();
    }

}

