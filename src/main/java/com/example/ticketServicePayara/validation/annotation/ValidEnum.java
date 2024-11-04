//package com.example.ticketServicePayara.validation.annotation;
//
//import com.example.ticketServicePayara.validation.validator.EnumValidator;
//import jakarta.validation.Constraint;
//import jakarta.validation.Payload;
//
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//
//@Constraint(validatedBy = EnumValidator.class)
//@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
//@Retention(RetentionPolicy.RUNTIME)
//public @interface ValidEnum {
//    String message() default "Недопустимое значение для перечисления";
//
//    Class<?>[] groups() default {};
//
//    Class<? extends Payload>[] payload() default {};
//
//    Class<? extends Enum<?>> enumClass(); // Класс перечисления
//}
