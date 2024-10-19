package com.example.ticketServicePayara.enums;

import java.util.List;


public enum TicketType {
    CHEAP("cheap"),
    BUDGETARY("budgetary"),
    USUAL("usual"),
    VIP("vip");

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
}
