package org.example.ticketservice.converter;


import org.example.ticketservice.dto.TicketWrite;
import org.example.ticketservice.enums.TicketType;
import org.example.ticketservice.model.Ticket;

public class TicketWriteConverter {


    public static Ticket toTicket(TicketWrite ticketWrite) {
        Ticket ticket = new Ticket();
        ticket.setName(ticketWrite.getName());
        ticket.setPrice(ticketWrite.getPrice());
        if (ticketWrite.getType() != null) ticket.setType(TicketType.valueOf(ticketWrite.getType().toUpperCase()));
        ticket.setDiscount(ticketWrite.getDiscount());
        if (ticketWrite.getRefundable() != null) ticket.setRefundable(ticketWrite.getRefundable());
        else ticket.setRefundable(false);
        ticket.setCoordinates(CoordinatesWriteConverter.toCoordinates(ticketWrite.getCoordinates()));
        if (ticketWrite.getPerson() != null) ticket.setPerson(PersonWriteConverter.toPerson(ticketWrite.getPerson()));
        ticket.setEventId(0);
        return ticket;
    }


    public static TicketWrite toTicketWrite(Ticket ticket) {
        TicketWrite ticketWrite = new TicketWrite();
        ticketWrite.setName(ticket.getName());
        ticketWrite.setPrice(ticket.getPrice());
        if (ticket.getType() != null) ticketWrite.setType(ticket.getType().name().toUpperCase());
        ticketWrite.setDiscount(ticket.getDiscount());
        ticketWrite.setRefundable(ticket.isRefundable());
        ticketWrite.setCoordinates(CoordinatesWriteConverter.toCoordinatesWrite(ticket.getCoordinates()));
        if (ticket.getPerson() != null) ticketWrite.setPerson(PersonWriteConverter.toPersonWrite(ticket.getPerson()));
        return ticketWrite;
    }
}
