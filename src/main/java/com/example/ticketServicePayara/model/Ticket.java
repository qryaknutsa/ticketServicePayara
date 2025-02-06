package com.example.ticketServicePayara.model;

import com.example.ticketServicePayara.enums.TicketType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;


import java.io.Serializable;
import java.time.ZonedDateTime;



@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates", nullable = false)
    private Coordinates coordinates;

    @Column(name = "creationDate", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    @JsonProperty("creationDate")
    private ZonedDateTime creationDate = ZonedDateTime.now();

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private double discount;

    @Column
    private boolean refundable = false;

    @Enumerated(EnumType.STRING)
    @Column
    private TicketType type;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "person")
    private Person person;

    @Column(name = "eventId")
    private int eventId = 0;


    public Ticket() {
    }


    public Ticket(Ticket ticket) {
        this.name = ticket.getName();
        this.price = ticket.getPrice();
        this.coordinates = new Coordinates(ticket.getCoordinates());
        this.discount = ticket.getDiscount();
        this.refundable = ticket.isRefundable();
        if(ticket.getType() != null) this.type = ticket.getType();
        if (ticket.getPerson() != null) this.person = new Person(ticket.getPerson());
        this.eventId = ticket.getEventId();
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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public boolean isRefundable() {
        return refundable;
    }

    public void setRefundable(boolean refundable) {
        this.refundable = refundable;
    }

    public TicketType getType() {
        return type;
    }

    public void setType(TicketType type) {
        this.type = type;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
