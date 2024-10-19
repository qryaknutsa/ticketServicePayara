package com.example.ticketServicePayara.controller;

import com.example.ticketServicePayara.dto.TicketDto;
import com.example.ticketServicePayara.service.CoordinatesService;
import com.example.ticketServicePayara.service.LocationService;
import com.example.ticketServicePayara.service.PersonService;
import com.example.ticketServicePayara.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.NotActiveException;

@RestController
//@RequestMapping("${api.endpoints.base-url}/tickets")
@RequestMapping(value = "TMA/api/v2/tickets")
@RequiredArgsConstructor
@Validated
public class TicketController {

//    @Autowired
//    private Validator validator;

    private final TicketService ticketService;
    private final PersonService personService;
    private final CoordinatesService coordinatesService;
    private final LocationService locationService;


    @GetMapping(value = "hello")
    public ResponseEntity<?> getAllTickets() {
        return ResponseEntity.status(200).body("hello");
    }

//    @GetMapping(value = "hello")
//    public ResponseEntity<String> getAllTickets() {
//        URI uri = UriComponentsBuilder
//                .fromHttpUrl(ROUTE_MICROSERVICE_URL)
//                .port(ROUTE_MICROSERVICE_PORT)
//                .path("/api/v1/routes")
//                .queryParam("fromId", 123)
//                .queryParam("toId", 321)
//                .queryParam("sortField", "distance")
//                .queryParam("sortDirection", "asc")
//                .queryParam("size", 1)
//                .build()
//                .toUri();
//        return restTemplate.getForEntity(uri, String.class);
//    }

    @PostMapping
    public ResponseEntity<?> saveTicket(@Valid @RequestBody TicketDto ticketDto) throws NotActiveException{

//        System.out.println(request.getContextPath());
//
//        Set<ConstraintViolation<TicketDto>> violationsTicket = validator.validate(ticketDto);
//        Set<ConstraintViolation<CoordinatesDto>> violationsCoor = validator.validate(ticketDto.getCoordinatesDto());
//        Set<ConstraintViolation<PersonDto>> violationsPerson = Set.of();
//        Set<ConstraintViolation<LocationDto>> violationsLoc = Set.of();
//        if(ticketDto.getPersonDto() != null) {
//            violationsPerson = validator.validate(ticketDto.getPersonDto());
//            violationsLoc = validator.validate(ticketDto.getPersonDto().getLocationDto());
//        }
//
//        if (!(violationsTicket.isEmpty() & violationsCoor.isEmpty() & violationsPerson.isEmpty() &  violationsLoc.isEmpty())) {
//            List<String> errors = violationsTicket.stream()
//                    .map(ConstraintViolation::getMessage)
//                    .collect(Collectors.toList());
//            errors.addAll(violationsCoor.stream()
//                    .map(ConstraintViolation::getMessage)
//                    .toList());
//            errors.addAll(violationsPerson.stream()
//                    .map(ConstraintViolation::getMessage)
//                    .toList());
//            errors.addAll(violationsLoc.stream()
//                    .map(ConstraintViolation::getMessage)
//                    .toList());
//            ErrorResponseArray response = new ErrorResponseArray(ResponseEntity.unprocessableEntity().toString(), errors, request.getContextPath());
//            return ResponseEntity.badRequest().body(response);
//        }


        locationService.save(ticketDto.getPersonDto().getLocationDto());
        coordinatesService.save(ticketDto.getCoordinatesDto());
        personService.save(ticketDto.getPersonDto());
        ticketService.save(ticketDto);

        return ResponseEntity.status(201).body(ticketDto);
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<?> getTicketById(@PathVariable int id) throws NotActiveException {
        ticketService.findById(id);
        return ResponseEntity.status(200).body(ticketService.findById(id));
    }


    @PatchMapping(value = "{id}")
    public ResponseEntity<?> updateTicket(@PathVariable int id, @RequestBody TicketDto ticket) throws NotActiveException {
        TicketDto ticket1 = ticketService.findById(id);
//        ticketService.update(ticket1, ticket);
        ticketService.save(ticket1);
        return ResponseEntity.status(200).body(ticket);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<?> deleteTicketById(@PathVariable int id) throws NotActiveException {
        ticketService.deleteById(id);
        return ResponseEntity.status(204).body(ticketService.findById(id));
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
    public ResponseEntity<?> getUniqueTypes(@PathVariable String type) {
        try {
            return ResponseEntity.status(200).body(ticketService.getUniqueTypes(type));
        } catch (RuntimeException e){
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
}

