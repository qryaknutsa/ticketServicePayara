package com.example.ticketServicePayara.controller;

import com.example.ticketServicePayara.dao.TicketDao;
import com.example.ticketServicePayara.model.Ticket;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "TMA/api/v2/tickets")
@EnableTransactionManagement
@Validated
public class TicketController {

    @Autowired
    private TicketDao ticketService;


    @GetMapping(produces = "application/json")
    public ResponseEntity<?> getAllTickets() {
        List<Ticket> list = ticketService.getAll();
        return ResponseEntity.ok(list);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> saveTicket(@Valid @RequestBody Ticket ticket) {
        ticketService.save(ticket);
        return ResponseEntity.status(201).body(ticket);
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<?> getTicketById(@PathVariable int id) {
        return ResponseEntity.status(200).body(ticketService.getById(id));
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity<?> updateTicket(@PathVariable int id, @RequestBody Map<String, Object> updates)  {
        ticketService.update(id, updates);
        return ResponseEntity.status(200).body(id);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deleteTicketById(@PathVariable int id)  {
        ticketService.deleteById(id);
        return ResponseEntity.status(204).body(id);
    }

    @GetMapping(value = "discounts")
    public ResponseEntity<?> getDiscounts() {
        try {
            return ResponseEntity.status(200).body(ticketService.discountSum());
        } catch (RuntimeException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value = "types/less/{type}")
    public ResponseEntity<?> getLessTypes(@PathVariable String type) {
        try {
            return ResponseEntity.status(200).body(ticketService.getAmountLessThanType(type));
        } catch (RuntimeException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping(value = "types/unique")
    public ResponseEntity<?> getUniqueTypes() {
        try {
            return ResponseEntity.status(200).body(ticketService.getUniqueTypes());
        } catch (RuntimeException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}

