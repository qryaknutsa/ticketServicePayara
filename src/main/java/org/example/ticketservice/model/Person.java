package org.example.ticketservice.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.ticketservice.enums.Country;
import org.example.ticketservice.enums.EyeColor;
import org.example.ticketservice.enums.HairColor;

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

    @Column(nullable = false)
    private int height;

    @Enumerated(EnumType.STRING)
    @Column(name = "eyecolor")
    private EyeColor eyeColor;

    @Enumerated(EnumType.STRING)
    @Column(name = "haircolor", nullable = false)
    private HairColor hairColor;

    @Enumerated(EnumType.STRING)
    @Column
    private Country nationality;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name="location",nullable = false)
    private Location location;

    public Person() {
    }

    public Person(Person person) {
        this.height = person.getHeight();
        if(person.getEyeColor() != null) this.eyeColor = person.getEyeColor();
        this.hairColor = person.getHairColor();
        if(person.getNationality() != null) this.nationality = person.getNationality();
        this.location = new Location(person.getLocation());
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public EyeColor getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(EyeColor eyeColor) {
        this.eyeColor = eyeColor;
    }

    public HairColor getHairColor() {
        return hairColor;
    }

    public void setHairColor(HairColor hairColor) {
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
