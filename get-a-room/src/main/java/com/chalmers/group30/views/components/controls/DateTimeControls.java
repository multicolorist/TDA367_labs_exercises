package com.chalmers.group30.views.components.controls;

import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.html.Div;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * A component that contains a date-and-time picker
 */
public class DateTimeControls extends Div {
        public DateTimeControls() {
            DateTimePicker dateTimePicker = new DateTimePicker();
            // dateTimePicker.setLabel("Meeting date and time");
            dateTimePicker.setStep(Duration.ofMinutes(15));
            dateTimePicker.setValue(LocalDateTime.now().truncatedTo(ChronoUnit.HOURS).plusHours(1));
            add(dateTimePicker);
        }
}
