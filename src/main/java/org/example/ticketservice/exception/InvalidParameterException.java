package org.example.ticketservice.exception;

public class InvalidParameterException extends RuntimeException {
  public InvalidParameterException(String message) {
    super(message);
  }
}
