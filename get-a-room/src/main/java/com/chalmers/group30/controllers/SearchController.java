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

/**
 * Controller for the search query - relays the query to the model and returns the result
 */
// @Component
// @UIScope
public class SearchController {
    private final GetARoomFacadeInterface getARoomFacade;
    private final QueryContainer queryContainer;
    private final RecordDisplay recordDisplay;
    private SearchResult currentSearchResult;

    private SearchResult getSearchResults() throws IOException {
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

    private ComponentEventListener<ClickEvent<Button>> getExecuteSearchButtonListener() {
        return new SearchController.ExecuteSearchButtonListener();
    }

    public void updateResults() {
        try {
            currentSearchResult = getSearchResults();
            recordDisplay.setItems(currentSearchResult.results());
            recordDisplay.setCurrentSearchQueryDate(currentSearchResult.searchQuery().startTime().toLocalDate());
        } catch (IOException e) {
            e.printStackTrace();
            Notification.show("Could not get search result");
        }
    }

    private class ExecuteSearchButtonListener implements ComponentEventListener<ClickEvent<Button>> {
        @Override
        public void onComponentEvent(ClickEvent<Button> e) {
            Notification.show(String.format("This executes a search for %d people between %s to %s at %s.",
                    queryContainer.getGroupSize(),
                    queryContainer.getStartDateTime().toString(),
                    queryContainer.getEndDateTime().toString(),
                    queryContainer.getDate().toString()
            ));
            updateResults();
        }
    }
}
