package com.example.ticketServicePayara.model;


import com.example.ticketServicePayara.enums.Country;
import com.example.ticketServicePayara.enums.EyeColor;
import com.example.ticketServicePayara.enums.HairColor;
import jakarta.persistence.*;
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


    @Column(name = "height", nullable = false)
    private int height;

    @Enumerated(EnumType.STRING)
    @Column(name = "eyeColor")
    private EyeColor eyeColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "hairColor", nullable = false)
    private HairColor hairColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "nationality")
    private Country nationality;

    @ManyToOne // more than one people per location
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
