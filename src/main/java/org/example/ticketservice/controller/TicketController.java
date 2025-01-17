package org.example.ticketservice.controller;

import jakarta.validation.Valid;
import org.example.ticketservice.converter.TicketWithEventConverter;
import org.example.ticketservice.converter.TicketWriteConverter;
import org.example.ticketservice.dto.TicketWithEventWrite;
import org.example.ticketservice.dto.TicketWrite;
import org.example.ticketservice.dto.TicketWriteUpdate;
import org.example.ticketservice.model.*;
import org.example.ticketservice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "TMA/api/v2/tickets")
@EnableTransactionManagement
@Validated
@CrossOrigin(origins = "*")
public class TicketController {

    @Autowired
    private TicketService ticketService;


    @GetMapping("qwe")
    public ResponseEntity<?> getQwe() {
        return ResponseEntity.ok("2 service");
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getAllTickets(@RequestParam(required = false) Integer size, @RequestParam(required = false) Integer page, @RequestParam(required = false) String sort, @RequestParam(required = false) String filter) {
        List<Ticket> list = ticketService.getAllFilteredSortedPaginated(size, page, sort, filter);
        return ResponseEntity.ok(list);
    }


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveTicket(@Valid @RequestBody TicketWrite ticket) {
        Ticket t = ticketService.save(TicketWriteConverter.toTicket(ticket));
        return ResponseEntity.status(201).body(t);
    }


    @GetMapping(value = "{id}")
    public ResponseEntity<?> getTicketById(@PathVariable int id) {
        return ResponseEntity.status(200).body(ticketService.getById(id));
    }



    @PatchMapping(value = "{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTicket(@PathVariable int id, @RequestBody @Valid TicketWriteUpdate ticket) {
        Ticket t = ticketService.update(id, ticket);
        return ResponseEntity.status(203).body(t);
    }

    @DeleteMapping(value = "{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTicketById(@PathVariable int id) {
        ticketService.deleteById(id);
        return ResponseEntity.status(204).body(id);
    }

    @GetMapping(value = "discounts")
    public ResponseEntity<?> getDiscounts() {
        return ResponseEntity.status(200).body(ticketService.discountSum());
    }

    @GetMapping(value = "types/less/{type}")
    public ResponseEntity<?> getLessTypes(@PathVariable String type) {
        return ResponseEntity.status(200).body(ticketService.getAmountLessThanType(type));
    }

    @GetMapping(value = "types/unique")
    public ResponseEntity<?> getUniqueTypes() {
        return ResponseEntity.status(200).body(ticketService.getUniqueTypes());
    }

    @GetMapping(value = "events/{eventId}")
    public ResponseEntity<?> getTicketByEventId(@PathVariable int eventId) {
        return ResponseEntity.status(200).body(ticketService.getNumAllByEventId(eventId));
    }

    @PostMapping(value = "bulk/{num}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> saveTickets(@Valid @RequestBody TicketWithEventWrite ticket, @PathVariable int num) {
        List<Long> ids = ticketService.saveTickets(TicketWithEventConverter.toTicket(ticket), num);
        return ResponseEntity.status(201).body(ids);
    }

    @DeleteMapping(value = "bulk/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTicketsByEventId(@PathVariable int id) {
        ticketService.deleteTicketsByEventIds(id);
        return ResponseEntity.status(204).build();
    }


    @GetMapping(value = "people/{id}")
    public ResponseEntity<?> getPerson(@PathVariable int id) {
        return ResponseEntity.status(200).body(ticketService.getPersonById(id));
    }

}

