package com.bibounde.vprotovisdemo.util;

import com.vaadin.data.Validator;

public class OpacityValidator implements Validator {

    public void validate(Object value) throws InvalidValueException {
        if (!this.isValid(value)) {
            throw new InvalidValueException("Value must be a number in [0, 1]");
        }
    }

    public boolean isValid(Object value) {
        if (value instanceof String) {
            try {
                Double dValue = Double.valueOf((String) value);
                return dValue >= 0d && dValue <= 1d;
            } catch (NumberFormatException e) {
                return false;
            }
            
        }
        return false;
    }

}
