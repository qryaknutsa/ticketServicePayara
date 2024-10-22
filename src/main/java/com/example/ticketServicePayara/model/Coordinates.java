package com.example.ticketServicePayara.model;

import com.example.ticketServicePayara.validation.ValidFraction;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "coordinates")
@Getter
@Setter
public class Coordinates implements Serializable {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "x")
    @Min(value = -90, message = "Значение не может быть меньше -90 градусов.")
    @Max(value = 90, message = "Значение не может быть больше 90 градусов.")
    @ValidFraction(fraction = 3, message = "Значение должно иметь не более 3 знаков после запятой.")
    private float x;

    @Column(name = "y", nullable = false)
    @Min(value = -180, message = "Значение не может быть меньше -180 градусов.")
    @Max(value = 180, message = "Значение не может быть больше 180 градусов.")
    @ValidFraction(fraction = 3, message = "Значение должно иметь не более 3 знаков после запятой.")
    @NotNull
    private Float y;

    public Coordinates() {
    }

    public Coordinates(float x, Float y) {
        this.x = x;
        this.y = y;
    }
}
