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

    @Column(nullable = false)
    private Integer x;

    @Column
    private long y = 0;

    @Column(nullable = false)
    private double z;

    @Column(columnDefinition="TEXT")
    private String name;

    public Location() {
    }

    public Location(Location location) {
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        if(location.getName() != null) this.name = location.getName();
    }


    public Location(int x, Integer y, double z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }
}
