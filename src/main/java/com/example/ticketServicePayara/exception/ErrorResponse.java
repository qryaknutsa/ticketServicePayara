package com.example.ticketServicePayara.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ErrorResponse {
    private String title;
    private String detail;
    private String instance;
}
