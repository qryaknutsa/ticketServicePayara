package com.example.ticketServicePayara.model;

import com.example.ticketServicePayara.validation.annotation.CustomNotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Table(name = "location")
@Getter
@Setter
public class Location implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CustomNotNull
    @Min(value = -2147483648, message = "Значение не может быть меньше возможного -2147483648")
    @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647")
    @Column(nullable = false)
    private Integer x;

    @DecimalMin(value = "-9223372036854775808", message = "Значение не может быть меньше возможного -9223372036854775808")
    @DecimalMax(value = "9223372036854775807", message = "Значение не может быть больше возможного 9223372036854775807")
    @Column
    private long y = 0;

    @CustomNotNull
    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308")
    @Column(nullable = false)
    private Double z;

    @Size(message = "Значение должно быть до 2147483647 символов")
    @Column(columnDefinition="TEXT")
    private String name;

    public Location() {
    }

    public Location(int x, Integer y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
}
