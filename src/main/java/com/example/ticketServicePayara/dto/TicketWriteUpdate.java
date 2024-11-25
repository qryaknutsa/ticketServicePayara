package com.example.ticketServicePayara.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;

@Data
public class TicketWriteUpdate implements Serializable {
    @Size(min = 1, message = "Значение не должно быть пустым.")
    private String name;

    @Valid
    private CoordinatesWriteUpdate coordinates;

    @Positive(message = "Значение должен быть больше нуля")
    @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647")
    private Integer price;

    @DecimalMin(value = "0", message = "Значение не может быть меньше возможного 0")
    @DecimalMax(value = "100", message = "Значение не может быть больше возможного 100")
    private Double discount;

    private Boolean refundable;

    @Pattern(regexp = "(?i)^(CHEAP|VIP|USUAL|BUDGETARY)$", message = "Некорректный выбор типа билета'")
    private String type;

    @Valid
    private PersonWriteUpdate person;
}