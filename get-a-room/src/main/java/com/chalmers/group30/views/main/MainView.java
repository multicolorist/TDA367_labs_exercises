package com.chalmers.group30.views.main;

import com.chalmers.group30.controllers.*;
import com.chalmers.group30.views.components.QueryContainer;
import com.chalmers.group30.views.components.RecordList;
import com.chalmers.group30.views.components.buttons.DarkLightModeButton;
import com.chalmers.group30.views.components.buttons.FilterButton;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import java.io.IOException;

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
    RecordListController recordListController;
    // QueryContainerController queryContainerController;


    @Autowired
    public MainView(
            DarkLightModeButtonController darkLightModeButtonController,
            DarkLightModeButton darkLightModeButton,
            FilterButtonController filterButtonController,
            FilterButton filterButton,
            RecordListController recordListController) throws IOException {

        // Init fields from dependency injection
        this.darkLightModeButtonController = darkLightModeButtonController;
        this.darkLightModeButton = darkLightModeButton;
        this.filterButtonController = filterButtonController;
        this.filterButton = filterButton;
        this.recordListController = recordListController;
        // this.queryContainerController = queryContainerController;

        // Add styling for main view
        addClassNames(
                LumoUtility.Margin.Horizontal.AUTO,
                LumoUtility.Padding.Bottom.LARGE,
                LumoUtility.Padding.Horizontal.SMALL
        );

        // Init filter and dark/light mode button
        filterButton.addClickListener(filterButtonController.getListener());
        darkLightModeButton.addClickListener(darkLightModeButtonController.getListener());

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

        // Record list - needed for the QueryContainerController to be able to update the list with new results
        // TODO: Remove the dependency on the controller, RecordListController can be removed entirely
        RecordList recordList = new RecordList(recordListController);

        // Query container
        // TODO: Change this when the search query results is implemented
        QueryContainer queryContainer = new QueryContainer();
        // QueryContainerController queryContainerController = new QueryContainerController(searchService, recordList, queryContainer);
        QueryContainerController queryContainerController = new QueryContainerController(recordList, queryContainer);
        // List<SearchRecord> currentSearchResult = queryContainerController.getSearchResult();
        queryContainer.executeSearchButton.addClickListener(queryContainerController.getExecuteSearchButtonListener());
        //TODO: Do first search to populate list

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
    }
}