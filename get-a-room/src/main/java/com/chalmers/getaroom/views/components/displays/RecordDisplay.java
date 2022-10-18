package com.chalmers.getaroom.views.components.displays;

import com.chalmers.getaroom.controllers.ShowOnMapButtonController;
import com.chalmers.getaroom.models.objects.SearchRecord;
import com.chalmers.getaroom.views.Mediator;
import com.chalmers.getaroom.views.components.buttons.ShowOnMapButton;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * A component that displays a list of SearchRecords
 * Note that Vaadin has a bug with loading duplicated results:
 * https://github.com/vaadin/flow-components/issues/3547
 * The solution is to not allow duplicates in the list, or to
 * use a Grid instead to display the records.
 */
public class RecordDisplay extends VirtualList<SearchRecord> {
    ShowOnMapButtonController showOnMapButtonController;
    private LocalDate currentSearchQueryDate;
    private final Mediator mapMediator;

    public RecordDisplay(ShowOnMapButtonController showOnMapButtonController, Mediator mapMediator) throws IOException {
        // Init fields
        this.showOnMapButtonController = showOnMapButtonController;
        this.mapMediator = mapMediator;

        // Style
        addClassNames(
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.Padding.Top.MEDIUM,
                LumoUtility.Padding.Bottom.XLARGE,
                LumoUtility.Height.FULL,
                LumoUtility.Overflow.HIDDEN // To disable side-scroll
        );

        // Set renderer to display each element
        setRenderer(new ComponentRenderer<>(this::listEntryProvider));
    }

    /**
     * Sets the date of the current search query.
     * Used by the view to determined if when the room is free
     * depending on the current time viewing the results.
     *
     * @param date the date of the current search query
     */
    public void setCurrentSearchQueryDate(LocalDate date) {
        this.currentSearchQueryDate = date;
    }

    /**
     * Creates a list entry for each room it's given
     *
     * @param searchRecord The search record to create a list entry for
     * @return A component that represents the search record in the list
     */
    Component listEntryProvider(SearchRecord searchRecord) {
        // Buttons
        Button showMapButton = new ShowOnMapButton(searchRecord.room());
        showMapButton.addClickListener(showOnMapButtonController.getListener());
        showMapButton.addClickListener(e -> mapMediator.notify("mapCalled"));

        // Top of the entry
        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.addClassNames(
                LumoUtility.Width.FULL
        );
        // Top left part of the entry, seen both folded and unfolded
        VerticalLayout topLayoutLeft = new VerticalLayout();
        topLayoutLeft.addClassNames(
                LumoUtility.Gap.SMALL
        );
        topLayoutLeft.getElement().appendChild(ElementFactory.createStrong(searchRecord.room().name()));
        int distanceToRoom = (int) Math.round(searchRecord.birdsDistance());
        topLayoutLeft.add(new Div(new Text(String.format("%s meters away", distanceToRoom == 0 ? "-" : distanceToRoom))));
        // Top right part of the entry, seen both folded and unfolded
        VerticalLayout topLayoutRight = new VerticalLayout();
        topLayoutRight.addClassNames(
                LumoUtility.Gap.SMALL,
                LumoUtility.Padding.Top.MEDIUM,
                LumoUtility.Padding.Left.NONE,
                LumoUtility.Padding.Right.NONE
        );
        topLayoutRight.add(new Div(new Text(searchRecord.room().building())));
        if (Objects.equals(searchRecord.room().floor(), "")) { // floor is optional
            topLayoutRight.add(new Div(new Text("")));
        } else {
            topLayoutRight.add(new Div(new Text("Floor " + searchRecord.room().floor())));
        }
        topLayout.add(topLayoutLeft, topLayoutRight);

        // Bottom part of the entry, seen only when unfolded
        VerticalLayout bottomLayout = new VerticalLayout();
        bottomLayout.addClassNames(
                LumoUtility.Gap.SMALL,
                LumoUtility.Padding.Top.NONE
        );
        Span bottomWrappedRowContainer = new Span(); // Lower row for buttons
        bottomWrappedRowContainer.addClassNames(
                LumoUtility.AlignSelf.CENTER,
                LumoUtility.TextAlignment.RIGHT
        );
        String bottomInfo = "";
        boolean hasAddress = !Objects.equals(searchRecord.room().streetAddress(), "");
        boolean hasSeats = searchRecord.room().seats() != -1;
        if (hasAddress) {
            bottomInfo += searchRecord.room().streetAddress();
        }
        if (hasAddress && hasSeats) {
            bottomInfo += ", ";
        }
        if (hasSeats) { // seats is optional
            bottomInfo += searchRecord.room().seats() + " seats";
        }

        // Add booking info to foldout
        String bookingInfo = "";
        if (currentSearchQueryDate != null) { // Has not been set, should be a bug if it happens but just to make sure
            boolean isBookedOnQueryDate = false;
            if (searchRecord.bookings().size() > 0) {
                LocalDateTime firstBooking = searchRecord.bookings().get(0).startTime();
                if (firstBooking.toLocalDate().isEqual(currentSearchQueryDate)) {
                    isBookedOnQueryDate = true;
                }
                if (isBookedOnQueryDate) {
                    // Must be during the same day
                    String firstBookingString = firstBooking.getHour() + ":" + firstBooking.getMinute();
                    bottomInfo = String.format("Free until %s", firstBookingString);
                } else {
                    bookingInfo = "Free the rest of the day";
                }
            }
        }

        // Container that wraps (helps with mobile) for foldout
        bottomWrappedRowContainer.add(
                new Div(new Text(bottomInfo)),
                new Div(new Text(bookingInfo)),
                showMapButton
        );
        bottomLayout.add(
                new Hr(),
                bottomWrappedRowContainer
        );

        // Make a foldable panel of the entry
        Details foldablePanel = new Details(topLayout, bottomLayout);
        foldablePanel.addClassNames(
                "custom-full-width",
                LumoUtility.Padding.Vertical.NONE, // Perhaps too little? Important to weigh legibility vs. compactness
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.BoxShadow.SMALL, // https://vaadin.com/docs/latest/styling/lumo/utility-classes/#box-shadow
                LumoUtility.BorderRadius.SMALL
        );
        foldablePanel.getElement().setAttribute("aria-label", "A room from the search results");
        return foldablePanel;
    }
}