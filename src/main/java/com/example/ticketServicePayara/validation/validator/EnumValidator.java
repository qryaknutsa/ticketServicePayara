//package com.example.ticketServicePayara.validation.validator;
//
//import com.example.ticketServicePayara.validation.annotation.ValidEnum;
//import jakarta.validation.ConstraintValidator;
//import jakarta.validation.ConstraintValidatorContext;
//
//public class EnumValidator implements ConstraintValidator<ValidEnum, String> {
//    private Class<? extends Enum<?>> enumClass;
//
//    @Override
//    public void initialize(ValidEnum annotation) {
//        this.enumClass = annotation.enumClass();
//    }
//
//    @Override
//    public boolean isValid(String value, ConstraintValidatorContext context) {
//        if (value == null) {
//            return true; // Или используйте @NotNull для проверки на null
//        }
//
//        try {
//            Enum.valueOf(enumClass.asSubclass(Enum.class), value);
//            return true; // Значение валидно
//        } catch (IllegalArgumentException e) {
//            return false; // Значение не валидно
//        }
//    }
//}
