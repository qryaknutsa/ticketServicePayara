package com.example.ticketServicePayara.dto;

import com.example.ticketServicePayara.validation.annotation.CustomNotNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketWrite implements Serializable {
    @CustomNotNull
    @Size(min = 1, message = "Значение не должно быть пустым.")
    private String name;

    @CustomNotNull
    @Valid
    private CoordinatesWrite coordinates;

    @CustomNotNull
    @Positive(message = "Значение должен быть больше нуля")
    @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647")
    private Integer price;

    @CustomNotNull
    @DecimalMin(value = "1", message = "Значение не может быть меньше возможного 0")
    @DecimalMax(value = "100", message = "Значение не может быть больше возможного 100")
    private Double discount;

    private Boolean refundable = false;

    @Pattern(regexp = "(?i)^(CHEAP|VIP|USUAL|BUDGETARY)$", message = "Некорректный выбор типа билета'")
    private String type;

    @Valid
    private PersonWrite person;

}