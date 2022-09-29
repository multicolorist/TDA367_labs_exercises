package com.chalmers.group30.views.components;

import com.chalmers.group30.controllers.BookButtonController;
import com.chalmers.group30.controllers.RecordListController;
import com.chalmers.group30.controllers.ShowOnMapButtonController;
import com.chalmers.group30.models.objects.Location;
import com.chalmers.group30.models.objects.Room;
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
import java.util.List;
import java.util.Objects;

/**
 * A component that displays a list of SearchRecords
 * Note that Vaadin has a bug with loading duplicated results:
 * https://github.com/vaadin/flow-components/issues/3547
 * The solution is to not allow duplicates in the list, or to
 * use a Grid instead to display the records.
 */
public class RecordList extends VirtualList<Room> {

    RecordListController recordListController;
    BookButtonController bookButtonController;

    public RecordList(RecordListController recordListController) throws IOException {
        // Init fields
        this.recordListController = recordListController; // TODO: Remove once facade arrives, no need

        // Style
        addClassNames(
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.Padding.Top.MEDIUM,
                LumoUtility.Padding.Bottom.XLARGE,
                LumoUtility.Height.FULL
        );

        // Get data
        // TODO: Do not set data here when facade comes, from QueryContainerController makes more sense instead
        List<Room> rooms = recordListController.getRooms();

        // Set data
        setItems(rooms);
        setRenderer(new ComponentRenderer<>(this::listEntryProvider));
    }

    /**
     * Creates a list entry for each room it's given
     * @param room The room to create a list entry for
     * @return A component that represents the room in the list
     */
    Component listEntryProvider(Room room) {
        // Buttons
        Button bookButton = new BookButton(room.uuid());
        bookButton.addClickListener(bookButtonController.getListener());
        Button showMapButton = new ShowOnMapButton(room.uuid());
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
        topLayoutLeft.getElement().appendChild(ElementFactory.createStrong(room.name()));
        Location userLocation = new Location(57.696484034673915, 11.975264592149706); // TODO: Change this to the user's location from a controller
        int distanceToRoom = (int) Math.round(recordListController.getBirdsDistance(userLocation, room.location()));// TODO: Change this to the user's location from a controller
        //TODO: Change this distance to the one delivered in the SearchRecord, so RecordListController can be removed entirely
        topLayoutLeft.add(new Div(new Text(String.format("%s meters away", distanceToRoom))));
        // Top right part of the entry, seen both folded and unfolded
        VerticalLayout topLayoutRight = new VerticalLayout();
        topLayoutRight.addClassNames(
                LumoUtility.Gap.SMALL
        );
        topLayoutRight.add(new Div(new Text(room.building())));
        if (Objects.equals(room.floor(), "")) { // floor is optional
            topLayoutRight.add(new Div(new Text("")));
        } else {
            topLayoutRight.add(new Div(new Text("Floor " + room.floor())));
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
        if (!Objects.equals(room.streetAddress(), "")) {
            bottomInfo += room.streetAddress();
        }
        if (room.seats() != -1) { // seats is optional
            bottomInfo = bottomInfo + ", " + room.seats() + " seats";
        }
        bottomWrappedRowContainer.add(
                new Div(new Text(bottomInfo)),
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