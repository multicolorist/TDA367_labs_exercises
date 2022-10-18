package com.chalmers.getaroom.views.components.controls;

import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.theme.lumo.LumoUtility;

/**
 * A component that contains an integer-stepper without label
 * Min, max and default value can be set
 */
public class IntegerUnlabeledStepper extends Span {
    private final IntegerField integerField = new IntegerField();

    public IntegerUnlabeledStepper(int minValue, int maxValue, int defaultValue) {
        integerField.setValue(defaultValue);
        integerField.setHasControls(true);
        integerField.setMin(minValue);
        integerField.setMax(maxValue); // TODO: See what's the max number of people in a room in the API
        integerField.addClassNames(
                LumoUtility.Display.INLINE_FLEX
        );
        add(integerField);
    }

    public int getValue() {
        return integerField.getValue();
    }

    public int getMinValue() {
        return integerField.getMin();
    }

    public int getMaxValue() {
        return integerField.getMax();
    }
}
