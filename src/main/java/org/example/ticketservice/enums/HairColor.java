package org.example.ticketservice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HairColor {
    RED("RED"),
    BLACK("BLACK"),
    BLUE("BLUE"),
    ORANGE("ORANGE"),
    WHITE("WHITE");

    private final String value;

    HairColor(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static HairColor fromValue(String value) {
        for (HairColor country : values()) {
            if (country.value.equals(value)) {
                return country;
            }
        }
        throw new IllegalArgumentException("Invalid country value: " + value);
    }
}