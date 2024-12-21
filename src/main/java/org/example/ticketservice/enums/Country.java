package org.example.ticketservice.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Country {
    NORTH_KOREA("NORTH_KOREA"),
    JAPAN("JAPAN"),
    CHINA("CHINA");

    private final String value;

    Country(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }


    @JsonCreator
    public static Country fromValue(String value) {
        for (Country country : values()) {
            if (country.value.equals(value)) {
                return country;
            }
        }
        throw new IllegalArgumentException("Invalid country value: " + value);
    }
}
