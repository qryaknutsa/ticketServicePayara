package org.example.ticketservice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum EyeColor {
    GREEN("GREEN"),
    RED("RED"),
    BLUE("BLUE");

    private final String value;

    EyeColor(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static EyeColor fromValue(String value) {
        for (EyeColor country : values()) {
            if (country.value.equals(value)) {
                return country;
            }
        }
        throw new IllegalArgumentException("Invalid country value: " + value);
    }
}