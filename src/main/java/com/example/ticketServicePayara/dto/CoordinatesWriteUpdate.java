package com.example.ticketServicePayara.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.io.Serializable;

@Data
public class CoordinatesWriteUpdate implements Serializable {
    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного 1.4E-45.")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 3.4028235E38.")
    private Float x;

    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308.")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308.")
    private Float y;
}