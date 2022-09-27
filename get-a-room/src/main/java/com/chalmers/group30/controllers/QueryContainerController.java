package com.chalmers.group30.controllers;

import com.chalmers.group30.models.BookingServiceInterface;
import com.chalmers.group30.models.RoomServiceInterface;
import com.chalmers.group30.models.RouteServiceInterface;
import com.chalmers.group30.models.objects.*;
import com.chalmers.group30.views.components.QueryContainer;
import com.chalmers.group30.views.utilities.TimeUtils;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.spring.annotation.UIScope;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * Controller for the search query - relays the query to the model and returns the result
 */
// @Component
// @UIScope
public class QueryContainerController {
    // TODO: Use the GetARoomFacade/searchService once it's in place
    // private List<SearchRecord> searchResult;
    int searchResult; // TODO: Remove this and use the above instead
    private final QueryContainer queryContainer;
    private final VirtualList<Room> recordList;

    // TODO: Add the below also
    // private final SearchServiceInterface searchService;
    //
    // public QueryContainerController(VirtualList<Room> recordList, QueryContainer queryContainer) {
    //     this.searchService = new SearchService(); // Cannot be dep. injected
    //     this.queryContainer = queryContainer;
    //     this.recordList = recordList;
    // }

    // TODO: Write when the GetARoomFacade/searchService is in place
    // public List<SearchRecord> getSearchResult() throws IOException {
    //     // TODO: Add user location when available
    //     Location userLocation = new Location(57.708870, 11.974560);
    //     SearchQuery searchQuery = new SearchQuery(
    //             userLocation,
    //             queryContainer.getGroupSize(),
    //             TimeUtils.localTimeToInstant(queryContainer.getFromTime(), queryContainer.getDate()),
    //             TimeUtils.localTimeToInstant(queryContainer.getUntilTime(), queryContainer.getDate())
    //     );
    //     return searchService(searchQuery);
    // }

    public QueryContainerController(VirtualList<Room> recordList, QueryContainer queryContainer) {
        this.queryContainer = queryContainer;
        this.recordList = recordList;
    }

    public ComponentEventListener<ClickEvent<Button>> getExecuteSearchButtonListener() {
        return new QueryContainerController.ExecuteSearchButtonListener();
    }

    public int getSearchResult() {
        return this.searchResult;
    }
    public void setSearchResult(int searchResult) {
        this.searchResult = searchResult;
    }

    // TODO: Add this when backend available
    // public void executeSearchAndUpdateList() {
    //     this.recordList.setItems(getSearchResult());
    // }

    private class ExecuteSearchButtonListener implements ComponentEventListener<ClickEvent<Button>> {
        @Override
        public void onComponentEvent(ClickEvent<Button> e) {
            Notification.show(String.format("This executes a search for %d people between %s to %s at %s.",
                    queryContainer.getGroupSize(),
                    queryContainer.getFromTime().toString(),
                    queryContainer.getUntilTime().toString(),
                    queryContainer.getDate().toString()
            ));
            setSearchResult(2);
            // TODO: Add this when backend available
            // executeSearchAndUpdateList();
        }
    }
}
