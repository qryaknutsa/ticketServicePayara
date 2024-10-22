package com.example.ticketServicePayara.controller;

import com.example.ticketServicePayara.dao.CoordinatesDao;
import com.example.ticketServicePayara.dao.LocationDao;
import com.example.ticketServicePayara.dao.PersonDao;
import com.example.ticketServicePayara.dao.TicketDao;
import com.example.ticketServicePayara.dto.TicketDto;
import com.example.ticketServicePayara.mappers.CoordinatesMapper;
import com.example.ticketServicePayara.mappers.LocationMapper;
import com.example.ticketServicePayara.mappers.PersonMapper;
import com.example.ticketServicePayara.mappers.TicketMapper;
import com.example.ticketServicePayara.model.Ticket;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.NotActiveException;
import java.util.List;

@RestController
//@RequestMapping("${api.endpoints.base-url}/tickets")
@RequestMapping(value = "TMA/api/v2/tickets")
//@RequiredArgsConstructor
@EnableTransactionManagement
@Validated
public class TicketController {

//    @Autowired
//    private Validator validator;

    @Autowired
    private TicketDao ticketService;
    @Autowired
    private PersonDao personService;
    @Autowired
    private CoordinatesDao coordinatesService;
    @Autowired
    private LocationDao locationService;


    @GetMapping(value = "hello")
    public ResponseEntity<?> getAllTickets() {

        List<TicketDto> list = ticketService.getAll().stream().map(TicketMapper::toTicketDto).toList();
        return ResponseEntity.status(200).body(list);
    }

//    @PostMapping
//    @Transactional
//    public ResponseEntity<?> saveTicket(@Valid @RequestBody TicketDto ticketDto) throws NotActiveException{
//
////        System.out.println(request.getContextPath());
////
////        Set<ConstraintViolation<TicketDto>> violationsTicket = validator.validate(ticketDto);
////        Set<ConstraintViolation<CoordinatesDto>> violationsCoor = validator.validate(ticketDto.getCoordinatesDto());
////        Set<ConstraintViolation<PersonDto>> violationsPerson = Set.of();
////        Set<ConstraintViolation<LocationDto>> violationsLoc = Set.of();
////        if(ticketDto.getPersonDto() != null) {
////            violationsPerson = validator.validate(ticketDto.getPersonDto());
////            violationsLoc = validator.validate(ticketDto.getPersonDto().getLocationDto());
////        }
////
////        if (!(violationsTicket.isEmpty() & violationsCoor.isEmpty() & violationsPerson.isEmpty() &  violationsLoc.isEmpty())) {
////            List<String> errors = violationsTicket.stream()
////                    .map(ConstraintViolation::getMessage)
////                    .collect(Collectors.toList());
////            errors.addAll(violationsCoor.stream()
////                    .map(ConstraintViolation::getMessage)
////                    .toList());
////            errors.addAll(violationsPerson.stream()
////                    .map(ConstraintViolation::getMessage)
////                    .toList());
////            errors.addAll(violationsLoc.stream()
////                    .map(ConstraintViolation::getMessage)
////                    .toList());
////            ErrorResponseArray response = new ErrorResponseArray(ResponseEntity.unprocessableEntity().toString(), errors, request.getContextPath());
////            return ResponseEntity.badRequest().body(response);
////        }
//
//
//        locationService.save(LocationMapper.toLocation(ticketDto.getPersonDto().getLocationDto()));
//        coordinatesService.save(CoordinatesMapper.toCoordinates(ticketDto.getCoordinatesDto()));
//        personService.save(PersonMapper.toPerson(ticketDto.getPersonDto()));
//        ticketService.save(TicketMapper.toTicket(ticketDto));
//
//        return ResponseEntity.status(201).body(ticketDto);
//    }


    @PostMapping
    @Transactional
    public ResponseEntity<?> saveTicket(@RequestBody Ticket ticket){
        locationService.save(ticket.getPerson().getLocation());
        coordinatesService.save(ticket.getCoordinates());
        personService.save(ticket.getPerson());
        ticketService.save(ticket);
        return ResponseEntity.status(201).body(ticket);
    }

//    @GetMapping(value = "{id}")
//    public ResponseEntity<?> getTicketById(@PathVariable int id) throws NotActiveException {
//        ticketService.findById(id);
//        return ResponseEntity.status(200).body(ticketService.findById(id));
//    }
//
//
//    @PatchMapping(value = "{id}")
//    public ResponseEntity<?> updateTicket(@PathVariable int id, @RequestBody TicketDto ticket) throws NotActiveException {
//        TicketDto ticket1 = ticketService.findById(id);
////        ticketService.update(ticket1, ticket);
//        ticketService.save(ticket1);
//        return ResponseEntity.status(200).body(ticket);
//    }
//
//    @DeleteMapping(value = "{id}")
//    public ResponseEntity<?> deleteTicketById(@PathVariable int id) throws NotActiveException {
//        ticketService.deleteById(id);
//        return ResponseEntity.status(204).body(ticketService.findById(id));
//    }
//
//    @GetMapping(value = "discounts")
//    public ResponseEntity<?> getDiscounts() {
//        try {
//            return ResponseEntity.status(200).body(ticketService.discountSum());
//        } catch (RuntimeException e){
//            return ResponseEntity.status(500).body(e.getMessage());
//        }
//    }
//
//    @GetMapping(value = "types/less/{type}")
//    public ResponseEntity<?> getUniqueTypes(@PathVariable String type) {
//        try {
//            return ResponseEntity.status(200).body(ticketService.getUniqueTypes(type));
//        } catch (RuntimeException e){
//            return ResponseEntity.status(500).body(e.getMessage());
//        }
//    }
}

