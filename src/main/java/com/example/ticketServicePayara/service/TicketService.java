package com.example.ticketServicePayara.service;

import com.example.ticketServicePayara.dto.TicketDto;
import com.example.ticketServicePayara.enums.TicketType;
import com.example.ticketServicePayara.mappers.TicketMapper;
import com.example.ticketServicePayara.model.Ticket;
import com.example.ticketServicePayara.dao.TicketDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.NotActiveException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final TicketDao ticketDao;

    public List<TicketDto> findAll() {
        return ticketDao.getAll().stream().map(TicketMapper::toTicketDto).toList();
    }

    public TicketDto findById(int id) throws NotActiveException {
        return TicketMapper.toTicketDto(ticketDao.getById(id));
    }

    public TicketDto save(TicketDto ticketDto) {
        return TicketMapper.toTicketDto(ticketDao.save(TicketMapper.toTicket(ticketDto)));
    }


    public void deleteById(int id) {
        ticketDao.deleteById(id);
    }

    public void delete(TicketDto ticketDto) {
        ticketDao.delete(TicketMapper.toTicket(ticketDto));
    }


    // Доп операции
    public double discountSum() {
        return ticketDao.getAll().stream()
                .mapToDouble(Ticket::getDiscount)
                .sum();
    }

    public long getAmountLessThanType(TicketType type){
        List<TicketType> list = TicketType.getLessTypes(type);
        return ticketDao.getAll().stream().filter(ticket -> list.contains(ticket.getType())).count();
    }

    public Set<String> getUniqueTypes(String type){
        return ticketDao.getAll().stream()
                .filter(t -> t.getType().name().equals(type))
                .map(ticket -> ticket.getType().name())
                .collect(Collectors.toSet());
    }

}