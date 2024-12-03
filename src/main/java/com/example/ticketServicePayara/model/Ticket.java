package com.example.ticketServicePayara.model;

import com.example.ticketServicePayara.enums.TicketType;
import com.example.ticketServicePayara.validation.annotation.CustomNotNull;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;


import java.io.Serializable;
import java.time.ZonedDateTime;


@Data
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {
    @Id
    @Column(nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CustomNotNull
    @Size(min = 1, message = "Значение не должно быть пустым.")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String name;

    @CustomNotNull
    @Valid
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "coordinates", nullable = false)
    private Coordinates coordinates;

    @Column(name = "creationDate", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private ZonedDateTime creationDate = ZonedDateTime.now();

    @CustomNotNull
    @Positive(message = "Значение должен быть больше нуля")
    @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647")
    @Column(nullable = false)
    private Integer price;

    @CustomNotNull
    @DecimalMin(value = "0", message = "Значение не может быть меньше возможного 0")
    @DecimalMax(value = "100", message = "Значение не может быть больше возможного 100")
    @Column(nullable = false)
    private Double discount;

    @Column
    private Boolean refundable;

    @Enumerated(EnumType.STRING)
    @Column
    private TicketType type;

    @Valid
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
        if(ticket.getRefundable() != null) this.refundable = ticket.getRefundable();
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
}
