package com.chalmers.group30.views.components;

import com.chalmers.group30.views.components.buttons.ExecuteSearchButton;
import com.chalmers.group30.views.components.controls.DatePickerControl;
import com.chalmers.group30.views.components.controls.IntegerUnlabeledStepper;
import com.chalmers.group30.views.components.controls.TimePickerControl;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * A container for the search query
 */
// @Component
// @UIScope
public class QueryContainer extends Div {
    public ExecuteSearchButton executeSearchButton;
    private final TimePickerControl startTimePicker;
    private final TimePickerControl endTimePicker;
    private final DatePickerControl datePicker;
    private final IntegerUnlabeledStepper groupSizeStepper;
    public QueryContainer() {
        // Style
        addClassNames(
                LumoUtility.Padding.Horizontal.MEDIUM,
                LumoUtility.JustifyContent.CENTER,
                LumoUtility.AlignItems.CENTER,
                LumoUtility.AlignSelf.CENTER,
                LumoUtility.Background.BASE,
                LumoUtility.FontSize.LARGE,
                LumoUtility.FontWeight.SEMIBOLD
        );

        // Initialize components
        startTimePicker = new TimePickerControl(0);
        endTimePicker = new TimePickerControl(2);
        datePicker = new DatePickerControl();
        groupSizeStepper = new IntegerUnlabeledStepper(1, 12, 4);
        executeSearchButton = new ExecuteSearchButton();

        // Add to container
        add(
                new Span(new Text("Rooms for ")),
                groupSizeStepper,
                new Span(new Text(" people from ")),
                startTimePicker,
                new Span(new Text(" until ")),
                endTimePicker,
                new Span(new Text(" at ")),
                datePicker,
                new Span(new Text(" and ")),
                executeSearchButton
        );
    }

    public int getGroupSize() {
        return groupSizeStepper.getValue();
    }
    public LocalDateTime getStartDateTime() {
        return startTimePicker.getValue().atDate(getDate());
    }
    public LocalDateTime getEndDateTime() {
        return endTimePicker.getValue().atDate(getDate());
    }
    public LocalDate getDate() {
        return datePicker.getValue();
    } // TODO: Make private?
}
