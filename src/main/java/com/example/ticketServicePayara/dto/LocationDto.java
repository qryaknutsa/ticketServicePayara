package com.example.ticketServicePayara.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto implements Serializable {

    @NotNull
    @Min(value = -99999, message = "Значение должно быть больше -1000000")
    @Max(value = 99999, message = "Значение должно быть меньше 1000000")
    private int x;

    @Min(value = -99999, message = "Значение должно быть больше -1000000")
    @Max(value = 99999, message = "Значение должно быть меньше -1000000")
    @Digits(integer = 5, fraction = 6, message = "Значение должно иметь максимум 6 знаков после запятой")
    private double y;

    @NotNull
    @Min(value = -99999, message = "Значение должно быть больше -1000000")
    @Max(value = 99999, message = "Значение должно быть меньше -1000000")
    @Digits(integer = 5, fraction = 6, message = "Значение должно иметь максимум 6 знаков после запятой")
    private double z;

    @Size(max = 100, message = "Значение должно быть до 100 символов")
    private String name;


    public LocationDto(int x, double z) {
        this.x = x;
        this.z = z;
    }

    public LocationDto(int x, double y, double z) {
        this.x = x;
        this.z = z;
        this.y = y;
    }

    public LocationDto(int x, double z, String name) {
        this.x = x;
        this.z = z;
        this.name = name;
    }

}
