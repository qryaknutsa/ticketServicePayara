package com.example.ticketServicePayara.dto;


import jakarta.validation.constraints.*;
import lombok.Data;

import java.io.Serializable;

@Data
public class LocationWriteUpdate implements Serializable {
    @Min(value = -2147483648, message = "Значение не может быть меньше возможного -2147483648")
    @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647")
    private Integer x;

    @DecimalMin(value = "-9223372036854775808", message = "Значение не может быть меньше возможного -9223372036854775808")
    @DecimalMax(value = "9223372036854775807", message = "Значение не может быть больше возможного 9223372036854775807")
    private Long y;

    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308")
    private Double z;

    @Size(message = "Значение должно быть до 2147483647 символов")
    private String name;
}
