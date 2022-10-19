package com.chalmers.getaroom.views.components;

import com.chalmers.getaroom.views.components.buttons.SearchButton;
import com.chalmers.getaroom.views.components.controls.DatePickerControl;
import com.chalmers.getaroom.views.components.controls.IntegerUnlabeledStepper;
import com.chalmers.getaroom.views.components.controls.TimePickerControl;
import com.chalmers.getaroom.views.utilities.TimeUtils;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * A container for the search query
 */
public class QueryContainer extends Div {
    private final SearchButton searchButton;
    private final TimePickerControl startTimePicker;
    private final TimePickerControl endTimePicker;
    private final DatePickerControl datePicker;
    private final IntegerUnlabeledStepper groupSizeStepper;

    public QueryContainer() {
        // Style
        addClassNames(
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.AlignSelf.CENTER,
                LumoUtility.Background.BASE,
                LumoUtility.FontSize.LARGE,
                LumoUtility.FontWeight.SEMIBOLD,
                LumoUtility.TextAlignment.CENTER
        );

        // Initialize components
        int minutesStepSize = 15;
        startTimePicker = new TimePickerControl(LocalTime.now(), minutesStepSize);
        startTimePicker.getElement().setAttribute("aria-label", "Pick start time for room search");
        // Ensure that end time initial value is not after midnight
        LocalTime endTime;
        Instant endTimeCandidate = Instant.now().plus(2, ChronoUnit.HOURS);
        if (endTimeCandidate.isAfter(TimeUtils.localTimeToInstant(LocalTime.of(23, 59), LocalDate.now()))) {
            endTime = LocalTime.of(23, 59 - minutesStepSize); // Was after midnight, so set to last choice
        } else {
            endTime = LocalTime.now().plus(2, ChronoUnit.HOURS); // Was today, so set to 2 hours from now
        }

        endTimePicker = new TimePickerControl(endTime, minutesStepSize);
        endTimePicker.getElement().setAttribute("aria-label", "Pick end time for room search");

        datePicker = new DatePickerControl();
        datePicker.getElement().setAttribute("aria-label", "Pick date for room search");

        groupSizeStepper = new IntegerUnlabeledStepper(1, 16, 4);
        groupSizeStepper.getElement().setAttribute("aria-label", "Select number of seats for room search");

        searchButton = new SearchButton();

        // Add to container
        add(
                new Span(new Text("Rooms for ")),
                groupSizeStepper,
                new Span(new Text(" people from ")),
                startTimePicker,
                new Span(new Text(" until ")),
                endTimePicker,
                new Span(new Text(" on ")),
                datePicker,
                new Span(new Text(" and ")),
                searchButton
        );
    }

    public void addSearchButtonListener(ComponentEventListener<ClickEvent<Button>> listener) {
        searchButton.addClickListener(listener);
    }

    public int getGroupSize() {
        return groupSizeStepper.getValue();
    }

    public int getMinGroupSize() {
        return groupSizeStepper.getMinValue();
    }

    public int getMaxGroupSize() {
        return groupSizeStepper.getMaxValue();
    }

    public LocalDateTime getStartDateTime() {
        return startTimePicker.getValue().atDate(getDate());
    }

    public LocalDateTime getEndDateTime() {
        return endTimePicker.getValue().atDate(getDate());
    }

    public LocalDate getDate() {
        return datePicker.getValue();
    }
}
