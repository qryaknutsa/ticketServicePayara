package com.example.ticketServicePayara.converter;

import com.example.ticketServicePayara.dto.TicketWithEventWrite;
import com.example.ticketServicePayara.dto.TicketWrite;
import com.example.ticketServicePayara.enums.TicketType;
import com.example.ticketServicePayara.model.Ticket;

public class TicketWithEventConverter {
    public static Ticket toTicket(TicketWithEventWrite ticketWrite) {
        Ticket ticket = new Ticket();
        ticket.setName(ticketWrite.getName());
        ticket.setPrice(ticketWrite.getPrice());
        if (ticketWrite.getType() != null) ticket.setType(TicketType.valueOf(ticketWrite.getType().toUpperCase()));
        ticket.setDiscount(ticketWrite.getDiscount());
        if (ticketWrite.getRefundable() != null) ticket.setRefundable(ticketWrite.getRefundable());
        ticket.setCoordinates(CoordinatesWriteConverter.toCoordinates(ticketWrite.getCoordinates()));
        if (ticketWrite.getPerson() != null) ticket.setPerson(PersonWriteConverter.toPerson(ticketWrite.getPerson()));
        if (ticketWrite.getEventId() != null) ticket.setEventId(ticketWrite.getEventId());
        else ticket.setEventId(0);
        return ticket;
    }


    public static TicketWithEventWrite toTicketWithEventWrite(Ticket ticket) {
        TicketWithEventWrite ticketWrite = new TicketWithEventWrite();
        ticketWrite.setName(ticket.getName());
        ticketWrite.setPrice(ticket.getPrice());
        if (ticket.getType() != null) ticketWrite.setType(ticket.getType().name().toUpperCase());
        ticketWrite.setDiscount(ticket.getDiscount());
        if (ticket.getRefundable() != null) ticketWrite.setRefundable(ticket.getRefundable());
        ticketWrite.setCoordinates(CoordinatesWriteConverter.toCoordinatesWrite(ticket.getCoordinates()));
        if (ticket.getPerson() != null) ticketWrite.setPerson(PersonWriteConverter.toPersonWrite(ticket.getPerson()));
        if(ticket.getEventId() != 0) ticketWrite.setEventId(ticket.getEventId());
        return ticketWrite;
    }
}
