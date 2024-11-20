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
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
public class PersonWrite implements Serializable {

    @CustomNotNull
    @Min(value = 50, message = "Значение должно быть больше 50")
    @Max(value = 300, message = "Значение должно быть меньше 300")
    private Integer height;

    private String eyeColor;

    @CustomNotNull
    private String hairColor;

    private String nationality;

    @CustomNotNull
    @Valid
    private LocationWrite location;
}