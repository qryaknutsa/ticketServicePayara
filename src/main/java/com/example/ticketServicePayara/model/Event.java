package com.example.ticketServicePayara.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "event")
public class Event implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title", nullable = false, columnDefinition="TEXT")
    private String title;

    @Column(name = "description", columnDefinition="TEXT")
    private String description;

    @Column(name = "startTime", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @JsonProperty("startTime")
    private ZonedDateTime startTime;

    @Column(name = "endTime", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @JsonProperty("endTime")
    private ZonedDateTime endTime;


    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates", nullable = false)
    private Coordinates coordinates;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "discount",nullable = false)
    @JsonProperty("discount")
    private Double discount;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "event_ticket",
            joinColumns = @JoinColumn(name = "event_id"),
            inverseJoinColumns = @JoinColumn(name = "ticket_id")
    )

    private List<Ticket> tickets = new ArrayList<>();

    public Event() {
    }

    public Event(String title, String description, ZonedDateTime startTime, ZonedDateTime endTime, Coordinates coordinates, Integer price, Double discount) {
        this.title = title;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
    }
}
