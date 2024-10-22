package com.example.ticketServicePayara.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

public enum EyeColor {
    GREEN("green"),
    RED("red"),
    BLUE("blue");

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
