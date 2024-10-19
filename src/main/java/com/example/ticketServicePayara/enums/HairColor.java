package com.example.ticketServicePayara.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HairColor {
    RED("red"),
    BLACK("black"),
    BLUE("blue"),
    ORANGE("orange"),
    WHITE("white");

    private final String value;

    HairColor(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static HairColor fromString(String color) {
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null");
        }
        return HairColor.valueOf(color.toUpperCase());
    }
}
