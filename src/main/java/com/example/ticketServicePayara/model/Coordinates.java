package com.example.ticketServicePayara.model;

import com.example.ticketServicePayara.validation.ValidFraction;
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
    @DecimalMin(value = "1.4E-45", message = "Значение не может быть меньше возможного 1.4E-45.")
    @DecimalMax(value = "3.4028235E38", message = "Значение не может быть больше возможного 3.4028235E38.")
    @ValidFraction(fraction = 3, message = "Значение должно иметь не более 3 знаков после запятой.")
    private float x = 0;

    @Column(nullable = false)
    @DecimalMin(value = "1.4E-45", message = "Значение не может быть меньше возможного 1.4E-45.")
    @DecimalMax(value = "3.4028235E38", message = "Значение не может быть больше возможного 3.4028235E38.")
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
