package org.example.ticketservice.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.ticketservice.validation.validator.CustomNotNullValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CustomNotNullValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomNotNull {

    String message() default "Поле не должно быть null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
