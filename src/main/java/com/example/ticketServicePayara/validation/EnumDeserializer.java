//package com.example.ticketServicePayara.validator;
//
//import com.example.ticketServicePayara.exception.InvalidEnumValueException;
//import com.fasterxml.jackson.core.JsonParser;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationContext;
//import com.fasterxml.jackson.databind.JsonDeserializer;
//
//import java.io.IOException;
//
//public class EnumDeserializer<E extends Enum<E>> extends JsonDeserializer<E> {
//    private final Class<E> enumClass;
//
//    public EnumDeserializer(Class<E> enumClass) {
//        this.enumClass = enumClass;
//    }
//
//    @Override
//    public E deserialize(JsonParser p, DeserializationContext ctxt)
//            throws IOException, JsonProcessingException {
//        String value = p.getText().trim();
//
//        try {
//            return Enum.valueOf(enumClass, value);
//        } catch (IllegalArgumentException e) {
//            throw new InvalidEnumValueException("Некорректное значение для перечисления: " + value);
//        }
//    }
//}
