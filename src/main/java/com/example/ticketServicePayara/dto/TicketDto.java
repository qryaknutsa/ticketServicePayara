package com.example.ticketServicePayara.dto;

import com.example.ticketServicePayara.enums.TicketType;
import com.example.ticketServicePayara.validation.EnumValid;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDto implements Serializable {
    @Size(min = 1, max = 100, message = "Значение должно быть до 100 символов")
    private String name;

    @JsonProperty("coordinates")
    @NotNull
    @Valid
    private CoordinatesDto coordinatesDto;

    @Positive(message = "Значение должен быть больше нуля")
    private int price;

    @Min(value = 1, message = "Значение должно быть больше 0")
    @Max(value = 100, message = "Значение должно быть не больше 100")
    @Digits(integer = 2, fraction = 6, message = "Значение должно иметь максимум 6 знаков после запятой")
    private double discount;

    private Boolean refundable;

    @JsonProperty("type")
    @EnumValid(enumClass = TicketType.class, message = "Некорректное значение для типа билета")
    private TicketType type;

    @JsonProperty("person")
    @Valid
    private PersonDto personDto;

    public TicketDto(String name, CoordinatesDto coordinatesDto, int price, double discount) {
        this.name = name;
        this.coordinatesDto = coordinatesDto;
        this.price = price;
        this.discount = discount;
        this.refundable = null;
        this.type = null;
        this.personDto = null;
    }

    public TicketDto(String name, CoordinatesDto coordinatesDto, int price, double discount, boolean refundable) {
        this.name = name;
        this.coordinatesDto = coordinatesDto;
        this.price = price;
        this.discount = discount;
        this.refundable = refundable;
        this.type = null;
        this.personDto = null;
    }
    public TicketDto(String name, CoordinatesDto coordinatesDto, int price, double discount, TicketType type) {
        this.name = name;
        this.coordinatesDto = coordinatesDto;
        this.price = price;
        this.discount = discount;
        this.type = type;
        this.personDto = null;
    }

    public TicketDto(String name, CoordinatesDto coordinatesDto, int price, double discount, PersonDto personDto) {
        this.name = name;
        this.coordinatesDto = coordinatesDto;
        this.price = price;
        this.discount = discount;
        this.personDto = personDto;
        this.type = null;
        this.refundable = null;
    }

    public TicketDto(String name, CoordinatesDto coordinatesDto, int price, double discount, boolean refundable, TicketType type) {
        this.name = name;
        this.coordinatesDto = coordinatesDto;
        this.price = price;
        this.discount = discount;
        this.refundable = refundable;
        this.type = type;
        this.personDto = null;
    }
    public TicketDto(String name, CoordinatesDto coordinatesDto, int price, double discount, TicketType type, PersonDto personDto) {
        this.name = name;
        this.coordinatesDto = coordinatesDto;
        this.price = price;
        this.discount = discount;
        this.type = type;
        this.personDto = personDto;
        this.refundable = null;
    }

    public TicketDto(String name, CoordinatesDto coordinatesDto, int price, double discount, boolean refundable, PersonDto personDto) {
        this.name = name;
        this.coordinatesDto = coordinatesDto;
        this.price = price;
        this.discount = discount;
        this.refundable = refundable;
        this.personDto = personDto;
        this.type = null;
    }

}

