package com.chalmers.group30.views.main;

import com.chalmers.group30.controllers.*;
import com.chalmers.group30.models.objects.SearchRecord;
import com.chalmers.group30.views.components.QueryContainer;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Room;
import com.chalmers.group30.views.components.buttons.BookButton;
import com.chalmers.group30.views.components.buttons.DarkLightModeButton;
import com.chalmers.group30.views.components.buttons.FilterButton;
import com.chalmers.group30.views.components.buttons.ShowOnMapButton;
import com.chalmers.group30.views.components.displays.DistancePill;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
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
    // QueryContainerController queryContainerController;

    // Class rendering the room list
    // TODO: Change this to rendering a list of SearchRecords instead when available, also name accordingly
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
        topLayoutLeft.add(new Div(distanceIndicator));
        // topLayoutLeft.add(new Div(new Text(distanceToRoom + " meters away")));

        // VerticalLayout topSpacer = new VerticalLayout();

        // Top right part of the card, seen both folded and unfolded
        VerticalLayout topLayoutRight = new VerticalLayout();
        topLayoutRight.add(new Div(new Text(room.building())));
        topLayoutRight.add(new Div(new Text("Floor " + room.floor())));

        // topLayout.add(topLayoutLeft, topSpacer, topLayoutRight);
        topLayout.add(topLayoutLeft, topLayoutRight);

        // HorizontalLayout textRow = new HorizontalLayout();
        // textRow.addClassNames(
        //         LumoUtility.AlignSelf.CENTER
        // );
        // textRow.add(new Div(new Text(room.streetAddress())));

        // Bottom part of the card, seen only when unfolded
        VerticalLayout bottomLayout = new VerticalLayout();
        Span listButtonContainer = new Span(); // Lower row for buttons

        listButtonContainer.add(
                showMapButton,
                bookButton
        );
        listButtonContainer.addClassNames(
                // LumoUtility.Display.FLEX,
                LumoUtility.AlignSelf.CENTER,
                LumoUtility.TextAlignment.RIGHT
        );
        // bottomLayout.add(textRow,listButtonContainer);
        bottomLayout.addClassNames(
                LumoUtility.Padding.Top.NONE
        );
        bottomLayout.add(
                new Hr(),
                listButtonContainer
        );

        // Add the content to the list
        // listEntryLayout.add(distanceIndicator, topLayout);
        listEntryLayout.add(topLayout);

        // Make a foldable panel of the entry
        Details foldablePanel = new Details(listEntryLayout, bottomLayout);
        // foldablePanel.addThemeVariants(DetailsVariant.FILLED);
        foldablePanel.addClassNames(
                "custom-full-width",
                LumoUtility.Padding.Vertical.NONE, // Perhaps too little? Important to weigh legibility vs. compactness
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.BoxShadow.SMALL, // https://vaadin.com/docs/latest/styling/lumo/utility-classes/#box-shadow
                LumoUtility.BorderRadius.SMALL
        );
        return foldablePanel;
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
        // this.queryContainerController = queryContainerController;

        // Add additional styling for main view
        // TODO: Is this best-practice in Vaadin?
        // addClassNames("main-view", "max-w-screen-lg", "mx-auto", "pb-l", "px-l");
        addClassNames(
                // "main-view",
                // LumoUtility.MaxWidth.SCREEN_XLARGE,
                LumoUtility.Margin.Horizontal.AUTO,
                LumoUtility.Padding.Bottom.LARGE,
                LumoUtility.Padding.Horizontal.SMALL
        );

        // Get initial search results
        // Data
        // TODO: Change this to the query below
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

        // Record list
        // TODO: Change this to virtuallist of SearchRecord
        VirtualList<Room> recordList = new VirtualList<>();
        recordList.addClassNames(
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.Padding.Top.MEDIUM,
                LumoUtility.Padding.Bottom.XLARGE
        );
        recordList.setItems(rooms);
        recordList.setRenderer(roomListEntryRenderer);
        recordList.setHeight("100%");

        // Query container
        // TODO: Change this when the search query results is implemented
        QueryContainer queryContainer = new QueryContainer();
        // QueryContainerController queryContainerController = new QueryContainerController(searchService, recordList, queryContainer);
        QueryContainerController queryContainerController = new QueryContainerController(recordList, queryContainer);
        // List<SearchRecord> currentSearchResult = queryContainerController.getSearchResult();
        queryContainer.executeSearchButton.addClickListener(queryContainerController.getExecuteSearchButtonListener());
        int currentSearchResult = queryContainerController.getSearchResult();

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
        navbarContainer.add(
                headerContainer,
                queryContainer
        );
        addToNavbar(navbarContainer);



        // Main view composition (header in navbar which is already a child)
        getElement().getStyle().set("height", "auto");
        setContent(
                recordList
        );

        // setSizeFull();
    }

}

