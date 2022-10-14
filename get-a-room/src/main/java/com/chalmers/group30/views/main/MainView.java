package com.chalmers.group30.views.main;

import com.chalmers.group30.controllers.*;
import com.chalmers.group30.models.GetARoomFacadeInterface;
import com.chalmers.group30.views.HasOpenableDrawer;
import com.chalmers.group30.views.MapMediator;
import com.chalmers.group30.views.components.GeolocationComponent;
import com.chalmers.group30.views.components.MapView;
import com.chalmers.group30.views.components.QueryContainer;
import com.chalmers.group30.views.components.buttons.DarkLightModeButton;
import com.chalmers.group30.views.components.buttons.AboutButton;
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
@CssImport(value = "./themes/getaroom/componentSpecific/vaadin-horizontal-layout.css", themeFor = "vaadin-horizontal-layout")

@PageTitle("GetARoom")
@Route(value = "")
@Component
@UIScope
public class MainView extends AppLayout implements HasComponents, HasStyle, HasOpenableDrawer {
    @Autowired
    public MainView(
            GetARoomFacadeInterface getARoomFacade,
            DarkLightModeButtonController darkLightModeButtonController,
            DarkLightModeButton darkLightModeButton,
            AboutButtonController aboutButtonController,
            AboutButton aboutButton,
            MapView mapView,
            ShowOnMapButtonController showOnMapButtonController,
            GeolocationComponent geolocationComponent,
            GeolocationComponentController geolocationComponentController
    ) throws IOException {

        // Add styling for main view
        addClassNames(
                LumoUtility.Margin.Horizontal.AUTO,
                LumoUtility.Padding.Bottom.LARGE,
                LumoUtility.Padding.Horizontal.SMALL
        );

        // Init filter and dark/light mode button
        aboutButton.addClickListener(aboutButtonController.getListener());
        darkLightModeButton.addClickListener(darkLightModeButtonController.getListener());

        // Header
        H3 title = new H3("GetARoom");
        title.addClassNames(
                "header",
                LumoUtility.Margin.Bottom.SMALL,
                LumoUtility.Margin.Top.SMALL
        );
        HorizontalLayout headerContainer = new HorizontalLayout();
        headerContainer.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.AlignSelf.CENTER,
                LumoUtility.Background.BASE,
                LumoUtility.FlexWrap.WRAP
        );
        headerContainer.setSpacing(false);
        headerContainer.add(
                aboutButton,
                title,
                darkLightModeButton
        );


        // Drawer with map
        Button closeDrawerButton = new Button("Close map view", new Icon(VaadinIcon.CLOSE), event -> {
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
        //TODO: Can avoid the mediator pattern here by passing "this" to RecordDisplay and changing there to a HasOpenableDrawer
        //TODO: However, it might be useful for other cases to use the mediator, for ex. more complex input validation
        RecordDisplay recordDisplay = new RecordDisplay(showOnMapButtonController, new MapMediator(this));

        // Geolocation
        geolocationComponentController = new GeolocationComponentController(geolocationComponent);

        // Query container - display results only on user-triggered search
        QueryContainer queryContainer = new QueryContainer();
        new SearchController(getARoomFacade, geolocationComponentController, recordDisplay, queryContainer); // init search controller

        // Navbar
        VerticalLayout navbarContainer = new VerticalLayout(); // To keep elements vertically ordered
        navbarContainer.addClassNames(
                LumoUtility.Width.FULL,
                LumoUtility.Padding.Horizontal.SMALL,
                LumoUtility.JustifyContent.BETWEEN, // horizontal
                LumoUtility.AlignItems.CENTER, // vertical
                LumoUtility.AlignSelf.CENTER,  // vertical
                LumoUtility.Background.BASE
        );
        navbarContainer.add(
                headerContainer,
                queryContainer
        );
        addToNavbar(navbarContainer, geolocationComponent);

        // Main view composition (header in navbar which is already a child)
        getElement().getStyle().set("height", "auto");
        setContent(
                recordDisplay
        );
    }

    @Override
    public void openDrawer() {
        setDrawerOpened(true);
    }
}