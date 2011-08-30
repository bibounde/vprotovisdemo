package com.bibounde.vprotovisdemo.util;

import com.vaadin.data.Validator;

public class ColorValidator implements Validator {

    private static final String REGEXP = "^#?([a-f]|[A-F]|[0-9]){3}(([a-f]|[A-F]|[0-9]){3})?$";
    
    public void validate(Object value) throws InvalidValueException {
        if (!this.isValid(value)) {
            throw new InvalidValueException("Invalid HTML color code. Valid value can be #FFFFFF");
        }
    }

    public boolean isValid(Object value) {
        return value instanceof String && ((String) value).matches(REGEXP);
    }

}
