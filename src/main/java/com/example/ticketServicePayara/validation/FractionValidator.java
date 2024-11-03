package com.example.ticketServicePayara.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FractionValidator implements ConstraintValidator<ValidFraction, Object> {
    private int fraction;

    @Override
    public void initialize(ValidFraction annotation) {
        this.fraction = annotation.fraction();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) return true;  // Допускаем null

        BigDecimal decimalValue;

        // Преобразование значения в BigDecimal в зависимости от его типа
        if (value instanceof BigDecimal) {
            decimalValue = (BigDecimal) value;
        } else if (value instanceof Double || value instanceof Float) {
            decimalValue = BigDecimal.valueOf(((Number) value).doubleValue());
        } else if (value instanceof Integer || value instanceof Long || value instanceof Short || value instanceof Byte) {
            decimalValue = BigDecimal.valueOf(((Number) value).longValue());
        } else {
            return false;  // Если тип не поддерживается, возвращаем false
        }

        // Округление и сравнение с исходным значением
        BigDecimal roundedValue = decimalValue.setScale(fraction, RoundingMode.HALF_UP);
        return roundedValue.compareTo(decimalValue) == 0;
    }
}

