package com.example.ticketServicePayara.dto;

import com.example.ticketServicePayara.enums.Country;
import com.example.ticketServicePayara.enums.EyeColor;
import com.example.ticketServicePayara.enums.HairColor;
import com.example.ticketServicePayara.validation.EnumValid;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonDto implements Serializable {
    @Min(value = 50, message = "Значение должно быть больше 50")
    @Max(value = 300, message = "Значение должно быть меньше 300")
    @NotNull
    private int height;


    @EnumValid(enumClass = EyeColor.class, message = "Некорректное значение для цвета глаз")
    private EyeColor eyeColor;

    @NotNull
    @EnumValid(enumClass = HairColor.class, message = "Некорректное значение для цвета волос")
    private HairColor hairColor;

    @EnumValid(enumClass = Country.class, message = "Некорректное значение для национальности")
    private Country nationality;

    @JsonProperty("location")
    @NotNull
    @Valid
    private LocationDto locationDto;


    public PersonDto(int height, HairColor hairColor, LocationDto locationDto) {
        this.height = height;
        this.hairColor = hairColor;
        this.locationDto = locationDto;
    }


    public PersonDto(int height, EyeColor eyeColor, HairColor hairColor, LocationDto locationDto) {
        this.height = height;
        this.hairColor = hairColor;
        this.locationDto = locationDto;
        this.eyeColor = eyeColor;
    }

    public PersonDto(int height, HairColor hairColor, Country nationality, LocationDto locationDto) {
        this.height = height;
        this.hairColor = hairColor;
        this.locationDto = locationDto;
        this.nationality = nationality;
    }

}
