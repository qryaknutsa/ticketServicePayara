package org.example.ticketservice.exception;

import lombok.Getter;

import java.util.List;

@Getter
public class BadPersonException extends RuntimeException {
    List<String> errors;
    public BadPersonException(List<String> errors) {
        this.errors= errors;
    }

}
