package com.chalmers.getaroom.controllers;

import com.chalmers.getaroom.models.GetARoomFacadeInterface;
import com.chalmers.getaroom.models.objects.Location;
import com.chalmers.getaroom.models.objects.SearchQuery;
import com.chalmers.getaroom.models.objects.SearchResult;
import com.chalmers.getaroom.views.components.QueryContainer;
import com.chalmers.getaroom.views.components.SearchResultComponent;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;

import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Controller for the search query - relays the query to the model and displays the result
 */
// @Component
// @UIScope
public class SearchController {
    private final GetARoomFacadeInterface getARoomFacade;
    private GeolocationComponentController geolocationComponentController;
    private final QueryContainer queryContainer;
    private final SearchResultComponent searchResultComponent;
    private final Logger logger = Logger.getLogger(SearchController.class.getName());

    private Location getUserLocation() {
        return geolocationComponentController.getLocation();
    }

    private SearchResult getSearchResults() throws IOException, IllegalArgumentException {
        Location userLocation;
        userLocation = getUserLocation();
        return getARoomFacade.search(new SearchQuery(userLocation, queryContainer.getGroupSize(), queryContainer.getStartDateTime(), queryContainer.getEndDateTime()));
    }

    public SearchController(GetARoomFacadeInterface getARoomFacade,
                            GeolocationComponentController geolocationComponentController,
                            SearchResultComponent searchResultComponent,
                            QueryContainer queryContainer) {
        this.getARoomFacade = getARoomFacade;
        this.geolocationComponentController = geolocationComponentController;
        this.queryContainer = queryContainer;
        this.searchResultComponent = searchResultComponent;
        ComponentEventListener<ClickEvent<Button>> listener = getSearchButtonListener();
        this.queryContainer.addSearchButtonListener(listener);
    }

    /**
     * Gets a listener for the corresponding button
     *
     * @return Listener for the button
     */
    private ComponentEventListener<ClickEvent<Button>> getSearchButtonListener() {
        return new SearchController.SearchButtonListener();
    }

    /**
     * Get search results and updates display accordingly
     */
    private void updateResults() {
        try {
            SearchResult searchResult = getSearchResults();
            searchResultComponent.setItems(searchResult.results());
            searchResultComponent.setCurrentSearchQueryDate(searchResult.searchQuery().startTime().toLocalDate());
            if (Double.isNaN(searchResult.searchQuery().userLocation().latitude())) {
                Notification.show(
                        "Please enable location services to see distance to rooms.",
                        6000,
                        Notification.Position.TOP_CENTER);
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not get search results.", e);
            Notification.show("Could not get search results, please check your connection or contact the authors.");
        }
    }

    private class SearchButtonListener implements ComponentEventListener<ClickEvent<Button>> {
        @Override
        public void onComponentEvent(ClickEvent<Button> e) {
            if (queryInputIsValid()) {
                Notification.show(
                        "Getting you many lovely rooms..",
                        4000,
                        Notification.Position.TOP_CENTER);
                updateResults();
            } else {
                Notification.show(
                        "Invalid input: Please ensure that the group size is at least 1, " +
                                "that the start time is before the end time on the same day, today or later.",
                        7000,
                        Notification.Position.TOP_CENTER);
            }
        }

        private boolean queryInputIsValid() {
            return queryContainer.getGroupSize() >= queryContainer.getMinGroupSize() &&
                    queryContainer.getGroupSize() <= queryContainer.getMaxGroupSize() &&
                    queryContainer.getStartDateTime().isBefore(queryContainer.getEndDateTime()) &&
                    queryContainer.getDate().isAfter(LocalDate.now().minusDays(1)); // Today or later
        }
    }
}
