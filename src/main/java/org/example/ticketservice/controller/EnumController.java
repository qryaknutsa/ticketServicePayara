package org.example.ticketservice.controller;


import org.example.ticketservice.enums.*;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "TMA/api/v2/enums")
@EnableTransactionManagement
@Validated
public class EnumController {
    @GetMapping("countries")
    public List<String> getCountries() {
        return Arrays.stream(Country.values())
                .map(Country::getValue)
                .collect(Collectors.toList());
    }

    @GetMapping("eye-colors")
    public List<String> getEyeColors() {
        return Arrays.stream(EyeColor.values())
                .map(EyeColor::getValue)
                .collect(Collectors.toList());
    }

    @GetMapping("hair-colors")
    public List<String> getHairColors() {
        return Arrays.stream(HairColor.values())
                .map(HairColor::getValue)
                .collect(Collectors.toList());
    }

    @GetMapping("ticket-types")
    public List<String> getTicketTypes() {
        return Arrays.stream(TicketType.values())
                .map(TicketType::getType)
                .collect(Collectors.toList());
    }
}
