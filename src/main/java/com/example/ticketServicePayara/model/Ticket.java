package com.example.ticketServicePayara.model;

import com.example.ticketServicePayara.enums.TicketType;
import com.example.ticketServicePayara.validation.ValidFraction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
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

    @Size(min = 1, message = "Значение должно быть от 1 до 2147483647 символов")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Valid
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "coordinates", nullable = false)
    private Coordinates coordinates;

    @Column(name = "creationDate", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    private ZonedDateTime creationDate = ZonedDateTime.now();

    @Positive(message = "Значение должен быть больше нуля")
    @Max(value = 2147483647, message = "Значение не может быть больше возможного 2147483647")
    @Column(nullable = false)
    private int price;

    //TODO: попроавить Swagger - required
    @DecimalMin(value = "4.9E-324", message = "Значение не может быть меньше возможного 4.9E-324")
    @DecimalMax(value = "1.7976931348623157E308", message = "Значение не может быть больше возможного 1.7976931348623157E308")
    @ValidFraction(fraction = 3, message = "Значение должно иметь не более 3 знаков после запятой.")
    @Column(nullable = false)
    private double discount;

    @Column
    private Boolean refundable;

    @Enumerated(EnumType.STRING)
    @Column
    private TicketType type;

    @Valid
    @OneToOne(cascade = CascadeType.PERSIST)
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
