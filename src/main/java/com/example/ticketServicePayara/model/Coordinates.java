package com.example.ticketServicePayara.model;

import com.example.ticketServicePayara.validation.annotation.CustomNotNull;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "coordinates")
@Getter
@Setter
public class Coordinates implements Serializable {
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного 1.4E-45.")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 3.4028235E38.")
    private float x = 0;

    @CustomNotNull
    @Column(nullable = false)
    @DecimalMin(value = "-1.79769313348623157E308", message = "Значение не может быть меньше возможного -1.79769313348623157E308.")
    @DecimalMax(value = "1.79769313348623157E308", message = "Значение не может быть больше возможного 1.79769313348623157E308.")
    private Float y;

    public Coordinates() {
    }

    public Coordinates(float x, Float y) {
        this.x = x;
        this.y = y;
    }
}
