package com.example.ticketServicePayara.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

public enum TicketType {
    @JsonValue
    CHEAP("CHEAP"),
    BUDGETARY("BUDGETARY"),
    USUAL("USUAL"),
    VIP("VIP");

    private final String value;

    TicketType(String value) {
        this.value = value;
    }

    public static List<TicketType> getLessTypes(TicketType type){
        if(type == VIP) return List.of(TicketType.CHEAP, TicketType.USUAL, TicketType.BUDGETARY);
        else if(type == USUAL) return List.of(TicketType.CHEAP, TicketType.BUDGETARY);
        else if(type == BUDGETARY) return List.of(TicketType.CHEAP);
        else return List.of();
    }

    @JsonValue
    public String getType() {
        return value;
    }


    @JsonCreator
    public static TicketType fromValue(String value) {
        for (TicketType country : values()) {
            if (country.value.equals(value)) {
                return country;
            }
        }
        throw new IllegalArgumentException("Invalid country value: " + value);
    }
}
