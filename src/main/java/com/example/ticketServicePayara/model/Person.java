package com.example.ticketServicePayara.model;


import com.example.ticketServicePayara.enums.Country;
import com.example.ticketServicePayara.enums.EyeColor;
import com.example.ticketServicePayara.enums.HairColor;
import com.example.ticketServicePayara.validation.annotation.CustomNotNull;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;

@Entity
@Table(name = "person")
@Getter
@Setter
public class Person implements Serializable {
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CustomNotNull
    @Min(value = 50, message = "Значение должно быть больше 50")
    @Max(value = 300, message = "Значение должно быть меньше 300")
    @Column(nullable = false)
    private Integer height;

    @Enumerated(EnumType.STRING)
    @Column(name = "eyeColor")
    private EyeColor eyeColor;

    @CustomNotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "hairColor", nullable = false)
    private HairColor hairColor;

    @Enumerated(EnumType.STRING)
    @Column
    private Country nationality;

    @CustomNotNull
    @Valid
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinColumn(name="location",nullable = false)
    private Location location;

    public Person() {
    }

    public Person(int height, EyeColor eyeColor, HairColor hairColor, Country nationality, Location location) {
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
        this.location = location;
    }

    public Person(int height, EyeColor eyeColor, HairColor hairColor, Country nationality) {
        this.height = height;
        this.eyeColor = eyeColor;
        this.hairColor = hairColor;
        this.nationality = nationality;
    }
}
