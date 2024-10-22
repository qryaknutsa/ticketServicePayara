package com.example.ticketServicePayara.model;

import com.example.ticketServicePayara.enums.TicketType;
//import com.example.ticketServicePayara.validation.EnumValid;
import com.example.ticketServicePayara.validation.ValidEnum;
import com.example.ticketServicePayara.validation.ValidFraction;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @Size(min = 1, max = 100, message = "Значение должно быть до 100 символов")
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Valid
    @ManyToOne // more than one ticket to one pair of coordinates
    @JoinColumn(name = "coordinates", nullable = false)
    private Coordinates coordinates;

    @Column(name = "creationDate", nullable = false)
    ZonedDateTime creationDate = ZonedDateTime.now();

    @Positive(message = "Значение должен быть больше нуля")
    @Column(name = "price", nullable = false)
    private int price;

    //TODO: попроавить Swagger - required
    @Min(value = 1, message = "Значение должно быть больше 0")
    @Max(value = 100, message = "Значение должно быть не больше 100")
    @ValidFraction(fraction = 3, message = "Значение должно иметь не более 3 знаков после запятой.")
    @Column(name = "discount", nullable = false)
    private double discount;

    @Column(name = "refundable")
    private Boolean refundable;

    @JsonProperty("type")
//    @ValidEnum(enumClass = TicketType.class, message = "Некорректное значение для типа билета")
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TicketType type;

    @Valid
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
