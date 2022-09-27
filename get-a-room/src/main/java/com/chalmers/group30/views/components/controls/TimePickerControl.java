package com.chalmers.group30.views.components.controls;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.Duration;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

/**
 * A component that contains a time picker meant to be displayed inline
 * The initial value is set to the next quarter of an hour.
 */
@CssImport(value = "./themes/getaroom/componentSpecific/vaadin-time-picker.css", themeFor = "vaadin-time-picker")
public class TimePickerControl extends Span {
    TimePicker timePicker = new TimePicker();

    /**
     * @param initialHourOffsetFromNow The initial value, offset from now (0 gives no offset)
     */
    public TimePickerControl(int initialHourOffsetFromNow) {
        timePicker.addClassNames(
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Display.INLINE_FLEX

        );
        timePicker.setStep(Duration.ofMinutes(15));
        timePicker.setValue(LocalTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(initialHourOffsetFromNow).plusMinutes(15 + 15 * (LocalTime.now().getMinute() / 15)));
        add(timePicker);
    }

    /**
     * @return The value of the time picker
     */
    public LocalTime getValue() {
        return timePicker.getValue();
    }
}

