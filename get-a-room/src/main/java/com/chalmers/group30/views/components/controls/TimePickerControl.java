package com.chalmers.group30.views.components.controls;

import com.chalmers.group30.views.utilities.TimeUtils;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * A component that contains a time picker meant to be displayed inline
 * The initial value is set to the next given step-size.
 */
@CssImport(value = "./themes/getaroom/componentSpecific/vaadin-time-picker.css", themeFor = "vaadin-time-picker")
public class TimePickerControl extends Span {
    TimePicker timePicker = new TimePicker();

    /**
     // * @param initialHourOffsetFromNow The initial value, offset from now (0 gives no offset)
     * @param initialTime The initial value
     * @param minutesStepSize The step size for the picker
     */
    public TimePickerControl(LocalTime initialTime, int minutesStepSize) {
        timePicker.addClassNames(
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Display.INLINE_FLEX

        );

        // Set step size
        timePicker.setStep(Duration.ofMinutes(minutesStepSize));

        // Set initial value (truncated to the nearest step-size of an hour)
        timePicker.setValue(initialTime
                .truncatedTo(ChronoUnit.HOURS)
                .plusMinutes(minutesStepSize + minutesStepSize * (initialTime.getMinute() / minutesStepSize)));

        add(timePicker);
    }

    /**
     * @return The value of the time picker
     */
    public LocalTime getValue() {
        return timePicker.getValue();
    }
}

