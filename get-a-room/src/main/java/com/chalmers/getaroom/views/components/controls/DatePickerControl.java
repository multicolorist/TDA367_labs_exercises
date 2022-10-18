package com.chalmers.getaroom.views.components.controls;

import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.time.LocalDate;

/**
 * A component that contains a date picker meant to be displayed inline
 * The initial value is set to today.
 */
@CssImport(value = "./themes/getaroom/componentSpecific/vaadin-date-picker.css", themeFor = "vaadin-date-picker")
public class DatePickerControl extends Span {
    private final DatePicker datePicker = new DatePicker();

    public DatePickerControl() {
        datePicker.addClassNames(
                LumoUtility.BorderRadius.MEDIUM,
                LumoUtility.Display.INLINE_FLEX
        );

        // Set custom format
        DatePicker.DatePickerI18n format = new DatePicker.DatePickerI18n();
        format.setDateFormat("d/M");
        datePicker.setI18n(format);

        // Set initial value to today
        datePicker.setValue(LocalDate.now());
        add(datePicker);
    }

    public LocalDate getValue() {
        return datePicker.getValue();
    }
}
