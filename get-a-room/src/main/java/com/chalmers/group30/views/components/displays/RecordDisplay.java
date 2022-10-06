package com.chalmers.group30.views.components.displays;

import com.chalmers.group30.controllers.BookButtonController;
import com.chalmers.group30.controllers.ShowOnMapButtonController;
import com.chalmers.group30.models.objects.SearchRecord;
import com.chalmers.group30.views.components.buttons.BookButton;
import com.chalmers.group30.views.components.buttons.ShowOnMapButton;
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
 *
 * Note that Vaadin has a bug with loading duplicated results:
 * https://github.com/vaadin/flow-components/issues/3547
 * The solution is to not allow duplicates in the list, or to
 * use a Grid instead to display the records.
 */
public class RecordDisplay extends VirtualList<SearchRecord> {
    private LocalDate currentSearchQueryDate;

    public RecordDisplay() throws IOException {
        // Style
        addClassNames(
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.Padding.Top.MEDIUM,
                LumoUtility.Padding.Bottom.XLARGE,
                LumoUtility.Height.FULL
        );

        // Set renderer
        setRenderer(new ComponentRenderer<>(this::listEntryProvider));
    }

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
        Button bookButton = new BookButton(searchRecord.room().uuid());
        bookButton.addClickListener(BookButtonController.getListener());
        Button showMapButton = new ShowOnMapButton(searchRecord.room().uuid());
        showMapButton.addClickListener(ShowOnMapButtonController.getListener());

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
        topLayoutLeft.add(new Div(new Text(String.format("%s meters away", distanceToRoom))));
        // Top right part of the entry, seen both folded and unfolded
        VerticalLayout topLayoutRight = new VerticalLayout();
        topLayoutRight.addClassNames(
                LumoUtility.Gap.SMALL
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

        // Add booking info
        // TODO: Should this be in a controller? Maybe a bit too much logic for a view?
        // TODO: Verify the logic once we have updated the booking cache to be more current
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

        // Container that wraps (helps with mobile)
        bottomWrappedRowContainer.add(
                new Div(new Text(bottomInfo)),
                new Div(new Text(bookingInfo)),
                showMapButton,
                bookButton
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
        return foldablePanel;
    }
}