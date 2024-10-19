package com.example.ticketServicePayara.dto;

import jakarta.validation.constraints.Digits;
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
public class CoordinatesDto implements Serializable {
    @Min(value = -90, message = "Значение не может быть меньше -90 градусов.")
    @Max(value = 90, message = "Значение не может быть больше 90 градусов.")
    @Digits(integer = 2, fraction = 6, message = "Значение до 2 цифр в целой части и до 6 цифр в дробной.")
    private float x;

    @Min(value = -180, message = "Значение не может быть меньше -180 градусов.")
    @Max(value = 180, message = "Значение не может быть больше 180 градусов.")
    @Digits(integer = 3, fraction = 6, message = "Значение до 3 цифр в целой части и до 6 цифр в дробной.")
    @NotNull
    private Float y;

    public CoordinatesDto(Float y) {
        this.y = y;
    }


}
