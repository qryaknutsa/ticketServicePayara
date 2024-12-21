package org.example.ticketservice.validation.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.example.ticketservice.validation.annotation.CustomNotNull;

public class CustomNotNullValidator implements ConstraintValidator<CustomNotNull, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value != null;
    }
}
