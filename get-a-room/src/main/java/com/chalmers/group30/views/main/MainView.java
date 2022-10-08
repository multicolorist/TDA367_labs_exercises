package com.chalmers.group30.views.main;

import com.chalmers.group30.controllers.*;
import com.chalmers.group30.models.GetARoomFacadeInterface;
import com.chalmers.group30.views.components.MapView;
import com.chalmers.group30.views.components.QueryContainer;
import com.chalmers.group30.views.components.buttons.DarkLightModeButton;
import com.chalmers.group30.views.components.buttons.FilterButton;
import com.chalmers.group30.views.components.displays.RecordDisplay;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@CssImport(value = "./themes/getaroom/componentSpecific/vaadin-details.css", themeFor = "vaadin-details")
@CssImport(value = "./themes/getaroom/componentSpecific/vaadin-app-layout.css", themeFor = "vaadin-app-layout")

@PageTitle("GetARoom")
@Route(value = "")
@Component
@UIScope
public class MainView extends AppLayout implements HasComponents, HasStyle {
    @Autowired
    public MainView(
            GetARoomFacadeInterface getARoomFacade,
            DarkLightModeButtonController darkLightModeButtonController,
            DarkLightModeButton darkLightModeButton,
            FilterButtonController filterButtonController,
            FilterButton filterButton,
            MapViewController mapViewController,
            MapView mapView) throws IOException {

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
        H3 title = new H3("GetARoom");
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
                filterButton,
                title,
                darkLightModeButton
        );



        // Drawer with map
        Button closeDrawerButton = new Button("Close map view", new Icon(VaadinIcon.CLOSE), event -> {
            // Notification.show("isOverlay: " + isOverlay());
            setDrawerOpened(false);
        });
        closeDrawerButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY,
                ButtonVariant.LUMO_CONTRAST); // To distinguish from other UI elements
        closeDrawerButton.addClassNames(
                LumoUtility.Display.BLOCK,
                LumoUtility.BoxShadow.SMALL,
                LumoUtility.BorderRadius.NONE
        );
        addToDrawer(closeDrawerButton, mapView);
        setDrawerOpened(false);

        // Record list - needed for the SearchController to be able to update the list with new results
        ShowOnMapButtonController showOnMapButtonController = new ShowOnMapButtonController(getARoomFacade, this, mapViewController);
        RecordDisplay recordDisplay = new RecordDisplay(showOnMapButtonController);

        // Query container - display results on site load
        QueryContainer queryContainer = new QueryContainer();
        SearchController searchController = new SearchController(getARoomFacade, recordDisplay, queryContainer);
        searchController.updateResults();

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
                recordDisplay
        );
    }
}