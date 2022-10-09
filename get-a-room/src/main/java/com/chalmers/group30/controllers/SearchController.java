package com.chalmers.group30.controllers;

import com.chalmers.group30.models.GetARoomFacadeInterface;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.SearchQuery;
import com.chalmers.group30.models.objects.SearchResult;
import com.chalmers.group30.views.components.QueryContainer;
import com.chalmers.group30.views.components.displays.RecordDisplay;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;

import java.io.IOException;
import java.time.LocalDate;

/**
 * Controller for the search query - relays the query to the model and displays the result
 */
// @Component
// @UIScope
public class SearchController {
    private final GetARoomFacadeInterface getARoomFacade;
    private final QueryContainer queryContainer;
    private final RecordDisplay recordDisplay;
    private SearchResult currentSearchResult; // TODO: Decide if this will be used for sorting/filtering, else remove

    private SearchResult getSearchResults() throws IOException, IllegalArgumentException {
        // TODO: Add user location when available
        Location userLocation = new Location(57.708870, 11.974560);
        return getARoomFacade.search(new SearchQuery(userLocation, queryContainer.getGroupSize(), queryContainer.getStartDateTime(), queryContainer.getEndDateTime()));
    }

    public SearchController(GetARoomFacadeInterface getARoomFacade, RecordDisplay recordDisplay, QueryContainer queryContainer) {
        this.getARoomFacade = getARoomFacade;
        this.queryContainer = queryContainer;
        this.recordDisplay = recordDisplay;
        this.queryContainer.executeSearchButton.addClickListener(getExecuteSearchButtonListener());
    }

    /**
     * Gets a listener for the corresponding button
     * @return Listener for the button
     */
    private ComponentEventListener<ClickEvent<Button>> getExecuteSearchButtonListener() {
        return new SearchController.ExecuteSearchButtonListener();
    }

    /**
     * Get search results and updates display accordingly
     */
    public void updateResults() {
        try {
            currentSearchResult = getSearchResults();
            recordDisplay.setItems(currentSearchResult.results());
            recordDisplay.setCurrentSearchQueryDate(currentSearchResult.searchQuery().startTime().toLocalDate());
        } catch (Exception e) {
            e.printStackTrace();
            Notification.show("Could not get search result");
        }
    }

    private class ExecuteSearchButtonListener implements ComponentEventListener<ClickEvent<Button>> {
        @Override
        public void onComponentEvent(ClickEvent<Button> e) {
            if (queryInputIsValid()) {
                Notification.show(String.format("This executes a search for %d people between %s to %s at %s.",
                        queryContainer.getGroupSize(),
                        queryContainer.getStartDateTime().toString(),
                        queryContainer.getEndDateTime().toString(),
                        queryContainer.getDate().toString()
                ));
                updateResults();
            } else {
                Notification.show(
                    "Invalid input: Please ensure that the group size is at least 1, " +
                            "that the start time is before the end time the same day, today or later.",
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
