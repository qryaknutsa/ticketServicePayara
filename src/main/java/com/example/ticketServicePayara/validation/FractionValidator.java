package com.example.ticketServicePayara.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FractionValidator implements ConstraintValidator<ValidFraction, Number> {
    private int fraction;

    @Override
    public void initialize(ValidFraction annotation) {
        this.fraction = annotation.fraction();
    }

    @Override
    public boolean isValid(Number value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Или используйте @NotNull для проверки на null
        }

        // Преобразуем число в строку

        String numberStr = String.valueOf(value);
        if (numberStr.contains(".")) {
            String[] parts = numberStr.split("\\.");
            return parts[1].length() <= fraction; // Проверяем количество знаков после запятой
        }
        return true; // Если нет дробной части, то значение корректно
    }
}

