package com.chalmers.group30.views.main;

import com.chalmers.group30.controllers.DarkLightModeButtonController;
import com.chalmers.group30.controllers.FilterButtonController;
import com.chalmers.group30.controllers.SearchController;
import com.chalmers.group30.models.GetARoomFacadeInterface;
import com.chalmers.group30.views.components.QueryContainer;
import com.chalmers.group30.views.components.buttons.DarkLightModeButton;
import com.chalmers.group30.views.components.buttons.FilterButton;
import com.chalmers.group30.views.components.displays.RecordDisplay;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

// Fix for Vaadin bug-ish that detail summary does not expand
// see for ex. https://vaadin.com/forum/thread/18151243/expanding-details-component
@CssImport(value = "./themes/getaroom/componentSpecific/vaadin-details.css", themeFor = "vaadin-details")

@PageTitle("GetARoom")
@Route(value = "")
public class MainView extends AppLayout implements HasComponents, HasStyle {
    @Autowired
    public MainView(
            GetARoomFacadeInterface getARoomFacade,
            DarkLightModeButtonController darkLightModeButtonController,
            DarkLightModeButton darkLightModeButton,
            FilterButtonController filterButtonController,
            FilterButton filterButton) throws IOException {

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
                filterButton,
                title,
                darkLightModeButton
        );

        // Record list - needed for the SearchController to be able to update the list with new results
        RecordDisplay recordDisplay = new RecordDisplay();

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