package com.example.ticketServicePayara.model;

import com.example.ticketServicePayara.validation.ValidFraction;
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

    @NotNull
    @Min(value = -99999, message = "Значение должно быть больше -1000000")
    @Max(value = 99999, message = "Значение должно быть меньше 1000000")
    @Column(name = "x", nullable = false)
    private int x;

    @Min(value = -99999, message = "Значение должно быть больше -1000000")
    @Max(value = 99999, message = "Значение должно быть меньше -1000000")
    @ValidFraction(fraction = 6, message = "Значение должно иметь не более 6 знаков после запятой.")
    @Column(name = "y")
    private double y;

    @NotNull
    @Min(value = -99999, message = "Значение должно быть больше -1000000")
    @Max(value = 99999, message = "Значение должно быть меньше -1000000")
    @ValidFraction(fraction = 6, message = "Значение должно иметь не более 6 знаков после запятой.")
    @Column(name = "z", nullable = false)
    private double z;

    @Size(max = 100, message = "Значение должно быть до 100 символов")
    @Column(name = "name")
    private String name;

    public Location() {
    }

    public Location(int x, double y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
}
