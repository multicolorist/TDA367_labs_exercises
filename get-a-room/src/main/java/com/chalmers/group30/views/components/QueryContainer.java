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
import java.time.LocalTime;

/**
 * A container for the search query
 */
// @Component
// @UIScope
public class QueryContainer extends Div {
    public ExecuteSearchButton executeSearchButton;
    private final TimePickerControl fromTimePicker;
    private final TimePickerControl untilTimePicker;
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
        fromTimePicker = new TimePickerControl(0);
        untilTimePicker = new TimePickerControl(2);
        datePicker = new DatePickerControl();
        groupSizeStepper = new IntegerUnlabeledStepper(1, 12, 4);
        executeSearchButton = new ExecuteSearchButton();

        // Add to container
        add(
                new Span(new Text("Rooms for ")),
                groupSizeStepper,
                new Span(new Text(" people from ")),
                fromTimePicker,
                new Span(new Text(" until ")),
                untilTimePicker,
                new Span(new Text(" at ")),
                datePicker,
                new Span(new Text(" and ")),
                executeSearchButton
        );
    }

    public int getGroupSize() {
        return groupSizeStepper.getValue();
    }
    public LocalTime getFromTime() {
        return fromTimePicker.getValue();
    }
    public LocalTime getUntilTime() {
        return untilTimePicker.getValue();
    }
    public LocalDate getDate() {
        return datePicker.getValue();
    }
}
