package com.chalmers.group30.views.components.controls;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.textfield.IntegerField;

/**
 * A component that contains an integer-stepper without label
 * Min, max and default value can be set
 */
public class IntegerUnlabeledStepper extends Div {
        public IntegerUnlabeledStepper(int minPersons, int maxPersons, int defaultValue) {
            IntegerField integerField = new IntegerField();
            integerField.setValue(defaultValue);
            integerField.setHasControls(true);
            integerField.setMin(minPersons);
            integerField.setMax(maxPersons); // TODO: See what's the max number of people in a room in the API
            add(integerField);
        }
}
