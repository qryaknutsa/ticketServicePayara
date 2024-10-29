package com.example.ticketServicePayara.model;


import com.example.ticketServicePayara.enums.Country;
import com.example.ticketServicePayara.enums.EyeColor;
import com.example.ticketServicePayara.enums.HairColor;
import com.example.ticketServicePayara.validation.ValidEnum;
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
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 50, message = "Значение должно быть больше 50")
    @Max(value = 300, message = "Значение должно быть меньше 300")
    @NotNull
    @Column(name = "height", nullable = false)
    private int height;

//    @ValidEnum(enumClass = EyeColor.class, message = "Некорректное значение для цвета глаз")
    @Enumerated(EnumType.STRING)
    @Column(name = "eyeColor")
    private EyeColor eyeColor;

    @NotNull
//    @ValidEnum(enumClass = HairColor.class, message = "Некорректное значение для цвета волос")
//    @ValidCountry(regexp = "^(CHINA|NORTH_KOREA|JAPAN)$", message = "Значение должно быть одно из: CHINA, NORTH_KOREA, JAPAN.")
    @Enumerated(EnumType.STRING)
    @Column(name = "hairColor", nullable = false)
    private HairColor hairColor;

//    @ValidEnum(enumClass = Country.class, message = "Некорректное значение для национальности")
//    @ValidCountry(regexp = "^(CHINA|NORTH_KOREA|JAPAN)$", message = "Значение должно быть одно из: CHINA, NORTH_KOREA, JAPAN.")
    @Enumerated(EnumType.STRING)
    @Column(name = "nationality")
    private Country nationality;

    @NotNull
    @Valid
    @ManyToOne(cascade = CascadeType.PERSIST) // more than one people per location
    @JoinColumn(name = "location", nullable = false)
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
