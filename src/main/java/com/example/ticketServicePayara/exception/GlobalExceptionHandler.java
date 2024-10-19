package com.example.ticketServicePayara.exception;

import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<Object> handleResourceNotFoundException(ChangeSetPersister.NotFoundException ex, WebRequest request) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("message", "Resource not found");
//
//        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
//    }



    // Обработка всех других исключений
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "An error occurred");

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }


//    @Override
//    protected ResponseEntity<Object> handleMethodArgumentNotValid(
//            MethodArgumentNotValidException ex,
//            HttpHeaders headers,
//            HttpStatus status,
//            WebRequest request) {
//        List<String> errors = new ArrayList<String>();
//        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
//            errors.add(error.getField() + ": " + error.getDefaultMessage());
//        }
//        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
//            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
//        }
//
//         ErrorResponseArray apiError =
//                new ErrorResponseArray(HttpStatus.BAD_REQUEST.name(), errors, request.getContextPath());
//        return handleExceptionInternal(
//                ex, apiError, headers, HttpStatus.BAD_REQUEST, request);
//    }


    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        System.out.println("handleMethodArgumentNotValid");

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ErrorResponseArray errorResponseArray =
                new ErrorResponseArray(HttpStatus.UNPROCESSABLE_ENTITY.name(), errors, request.getContextPath());

        return this.handleExceptionInternal(ex, errorResponseArray, headers, status, request);
    }


    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        System.out.println(ex.getHttpInputMessage());
        System.out.println(ex.getLocalizedMessage());
        System.out.println(ex.getMostSpecificCause().getLocalizedMessage());
        ProblemDetail body = this.createProblemDetail(ex, status, "Failed to read request", (String)null, (Object[])null, request);
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }
}
