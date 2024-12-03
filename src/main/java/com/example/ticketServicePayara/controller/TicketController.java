package com.example.ticketServicePayara.controller;

import com.example.ticketServicePayara.converter.TicketWriteConverter;
import com.example.ticketServicePayara.dao.TicketDao;
//import com.example.ticketServicePayara.dto.TicketIds;
import com.example.ticketServicePayara.dto.TicketWrite;
import com.example.ticketServicePayara.dto.TicketWriteUpdate;
import com.example.ticketServicePayara.model.Ticket;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    private TicketDao ticketService;


    @GetMapping
    public ResponseEntity<?> getAllTickets(@RequestParam(required = false) Integer size, @RequestParam(required = false) Integer page, @RequestParam(required = false) String sort, @RequestParam(required = false) String filter) {
        List<Ticket> list = ticketService.getAllFilteredSortedPaginated(size, page, sort, filter);
        return ResponseEntity.ok(list);
    }


    @PostMapping
    public ResponseEntity<?> saveTicket(@Valid @RequestBody TicketWrite ticket) {
        Ticket t = ticketService.save(TicketWriteConverter.toTicket(ticket));
        return ResponseEntity.status(201).body(t);
    }

    @PostMapping(value = "bulk/{num}")
    public ResponseEntity<?> saveTickets(@Valid @RequestBody TicketWrite ticket,@PathVariable int num){
        List<Integer> ids = ticketService.saveTickets(TicketWriteConverter.toTicket(ticket), num);
        return ResponseEntity.status(201).body(ids);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> getTicketById(@PathVariable int id) {
        return ResponseEntity.status(200).body(ticketService.getById(id));
    }

    @PatchMapping(value = "{id}")
    public ResponseEntity<?> updateTicket(@PathVariable int id, @RequestBody @Valid TicketWriteUpdate ticket) {
        ticketService.update(id, ticket);
        return ResponseEntity.status(203).body(id);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deleteTicketById(@PathVariable int id) {
        ticketService.deleteById(id);
        return ResponseEntity.status(204).body(id);
    }


    @DeleteMapping(value = "bulk/{id}")
    public ResponseEntity<?> deleteTicketsByEventId(@PathVariable int id){
        ticketService.deleteTicketsByIds(id);
        return ResponseEntity.status(204).build();
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


    @GetMapping(value = "people/{id}")
    public ResponseEntity<?> getPerson(@PathVariable int id) {
        return ResponseEntity.status(200).body(ticketService.getPersonById(id));
    }

}

