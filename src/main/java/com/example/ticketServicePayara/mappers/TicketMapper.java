package com.example.ticketServicePayara.mappers;

import com.example.ticketServicePayara.dto.TicketDto;
import com.example.ticketServicePayara.model.Ticket;

public class TicketMapper {

    public static TicketDto toTicketDto(Ticket ticket) {
        TicketDto dto = new TicketDto();
        dto.setName(ticket.getName());
        dto.setPrice(ticket.getPrice());
        dto.setCoordinatesDto(CoordinatesMapper.toCoordinatesDto(ticket.getCoordinates()));
        dto.setDiscount(ticket.getDiscount());
        if(ticket.getRefundable() != null) dto.setRefundable(ticket.getRefundable());
        if(ticket.getType() != null) dto.setType(ticket.getType());
        if(ticket.getPerson() != null) dto.setPersonDto(PersonMapper.toPersonDto(ticket.getPerson()));

        return dto;
    }
    public static Ticket toTicket(TicketDto dto) {
        Ticket ticket = new Ticket();
        ticket.setName(dto.getName());
        ticket.setPrice(dto.getPrice());
        ticket.setCoordinates(CoordinatesMapper.toCoordinates(dto.getCoordinatesDto()));
        ticket.setDiscount(dto.getDiscount());
        if(dto.getRefundable() != null) ticket.setRefundable(dto.getRefundable());
        if(dto.getType() != null) ticket.setType(dto.getType());
        if(dto.getPersonDto() != null) ticket.setPerson(PersonMapper.toPerson(dto.getPersonDto()));

        return ticket;
    }

}
