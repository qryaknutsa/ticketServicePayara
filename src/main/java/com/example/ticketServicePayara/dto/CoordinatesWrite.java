package com.example.ticketServicePayara.dto;

import com.example.ticketServicePayara.validation.annotation.CustomNotNull;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.io.Serializable;

@Data
public class CoordinatesWrite implements Serializable {
    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308.")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308.")
    private float x = 0;

    @CustomNotNull
    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308.")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308.")
    private Float y;
}