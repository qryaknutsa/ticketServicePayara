package com.example.ticketServicePayara.dto;

import com.example.ticketServicePayara.validation.annotation.CustomNotNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serializable;

@Data
public class PersonWriteUpdate implements Serializable {

    @Min(value = 50, message = "Значение должно быть больше 50")
    @Max(value = 300, message = "Значение должно быть меньше 300")
    private Integer height;
    @Pattern(regexp = "(?i)^(GREEN|RED|BLUE)$", message = "Некорректный выбор цвета глаз'")
    private String eyeColor;
    @Pattern(regexp = "(?i)^(BLACK|RED|BLUE|ORANGE|WHITE)$", message = "Некорректный выбор цвета волос'")
    private String hairColor;
    @Pattern(regexp = "(?i)^(china|japan|north_korea)$", message = "Некорректный выбор национальности'")
    private String nationality;

    @Valid
    private LocationWriteUpdate location;
}