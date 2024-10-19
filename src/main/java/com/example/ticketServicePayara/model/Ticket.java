package com.example.ticketServicePayara.model;

import com.example.ticketServicePayara.enums.TicketType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.ZonedDateTime;


@Setter
@Getter
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne // more than one ticket to one pair of coordinates
    @JoinColumn(name = "coordinates", nullable = false)
    private Coordinates coordinates;

    @Column(name = "creationDate", nullable = false)
    ZonedDateTime creationDate = ZonedDateTime.now();

    @Column(name = "price", nullable = false)
    private int price;

    //TODO: попроавить Swagger - required
    @Column(name = "discount", nullable = false)
    private double discount;

    @Column(name = "refundable")
    private Boolean refundable;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TicketType type;

    @ManyToOne // more than one ticket to one person
    @JoinColumn(name = "person")
    private Person person;

    public Ticket() {
    }

    public Ticket(String name, Coordinates coordinates, int price, double discount, Boolean refundable, TicketType type, Person person) {
        this.name = name;
        this.coordinates = coordinates;
        this.price = price;
        this.discount = discount;
        this.refundable = refundable;
        this.type = type;
        this.person = person;
    }

}
