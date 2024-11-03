package com.example.ticketServicePayara.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
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

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = CascadeType.MERGE) // more than one ticket to one pair of coordinates
    @JoinColumn(name = "coordinates", nullable = false)
    private Coordinates coordinates;

    @Column(name = "price", nullable = false)
    private int price;

    @OneToMany
    @JoinTable(
            name = "event_ticket",  // Имя таблицы-соединителя
            joinColumns = @JoinColumn(name = "event_id"),  // Колонка для связи с Event
            inverseJoinColumns = @JoinColumn(name = "ticket_id")  // Колонка для связи с Ticket
    )
    private List<Ticket> tickets = new ArrayList<>();

    public Event() {
    }
}
