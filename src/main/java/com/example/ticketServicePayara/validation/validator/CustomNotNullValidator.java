package com.example.ticketServicePayara.validation.validator;

import com.example.ticketServicePayara.validation.annotation.CustomNotNull;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CustomNotNullValidator implements ConstraintValidator<CustomNotNull, Object> {

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return value != null;
    }
}
