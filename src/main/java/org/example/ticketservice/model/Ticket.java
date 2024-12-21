package org.example.ticketservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.example.ticketservice.enums.TicketType;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "ticket")
@Data
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

    @Column(name = "creationdate", nullable = false)
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

    @Column(name = "eventid")
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



}
