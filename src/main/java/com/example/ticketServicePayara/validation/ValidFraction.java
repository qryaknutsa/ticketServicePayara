package com.example.ticketServicePayara.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FractionValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFraction {
    String message() default "Некорректное значение";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int fraction() default 2; // Максимальное количество знаков после запятой
}
