package com.example.ticketServicePayara.dto;

import com.example.ticketServicePayara.enums.Country;
import com.example.ticketServicePayara.enums.EyeColor;
import com.example.ticketServicePayara.enums.HairColor;
import com.example.ticketServicePayara.model.Location;
import com.example.ticketServicePayara.validation.annotation.CustomNotNull;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class PersonWrite implements Serializable {

    private Integer id;

    @CustomNotNull
    @Min(value = 50, message = "Значение должно быть больше 50")
    @Max(value = 300, message = "Значение должно быть меньше 300")
    private Integer height;

    @Pattern(regexp = "(?i)^(GREEN|RED|BLUE)$", message = "Некорректный выбор цвета глаз'")
    private String eyeColor;

    @CustomNotNull
    @Pattern(regexp = "(?i)^(BLACK|RED|BLUE|ORANGE|WHITE)$", message = "Некорректный выбор цвета волос'")
    private String hairColor;

    @Pattern(regexp = "(?i)^(china|japan|north_korea)$", message = "Некорректный выбор национальности'")
    private String nationality;

    @CustomNotNull
    @Valid
    private LocationWrite location;
}