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

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;


@Setter
@Getter
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private int id;

    @Size(min = 1, max = 100, message = "Значение должно быть до 100 символов")
    @Column(name = "name", nullable = false)
    @JsonProperty("name")
    private String name;

    @NotNull
    @Valid
    @ManyToOne(cascade = CascadeType.PERSIST) // more than one ticket to one pair of coordinates
    @JoinColumn(name = "coordinates", nullable = false)
    @JsonProperty("coordinates")
    private Coordinates coordinates;

    @Column(name = "creationDate", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSSX")
    @JsonProperty("creationDate")
    private ZonedDateTime creationDate = ZonedDateTime.now();

    @Positive(message = "Значение должен быть больше нуля")
    @Column(name = "price", nullable = false)
    @JsonProperty("price")
    private int price;

    //TODO: попроавить Swagger - required
    @Min(value = 1, message = "Значение должно быть больше 0")
    @Max(value = 100, message = "Значение должно быть не больше 100")
    @ValidFraction(fraction = 3, message = "Значение должно иметь не более 3 знаков после запятой.")
    @Column(name = "discount", nullable = false)
    @JsonProperty("discount")
    private double discount;

    @Column(name = "refundable")
    @JsonProperty("refundable")
    private Boolean refundable;

    @JsonProperty("type")
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TicketType type;

    @Valid
    @ManyToOne(cascade = CascadeType.PERSIST) // more than one ticket to one person
    @JoinColumn(name = "person")
    @JsonProperty("person")
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


    static class CustomZonedDateTimeDeserializer extends JsonDeserializer<ZonedDateTime> {
        @Override
        public ZonedDateTime deserialize(com.fasterxml.jackson.core.JsonParser p, DeserializationContext ctxt) throws IOException, IOException {
            String dateStr = p.getValueAsString();
            // Assuming the input is in the format "1729605412.289337000"
            // Convert it to a proper ZonedDateTime
            long epochSecond = Long.parseLong(dateStr.substring(0, dateStr.indexOf('.')));
            int nanoAdjustment = Integer.parseInt(dateStr.substring(dateStr.indexOf('.') + 1));
            Instant instant = Instant.ofEpochSecond(epochSecond, nanoAdjustment);
            return ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());
        }
    }

}
