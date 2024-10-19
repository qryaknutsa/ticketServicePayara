package com.example.ticketServicePayara.model;

import jakarta.persistence.*;
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

    @Column(name = "x", nullable = false)
    private int x;

    @Column(name = "y")
    private double y;

    @Column(name = "z", nullable = false)
    private double z;

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
