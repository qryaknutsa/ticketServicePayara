package com.example.ticketServicePayara.exception.tools;

import com.example.ticketServicePayara.exception.*;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import java.util.*;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    String UNPROCESSABLE_ENTITY = "Ошибка валидации данных";
    String BAD_REQUEST = "Некорректные данные";
    String INTERNAL_SERVER_ERROR = "Внутренняя ошибка сервера";
    String NOT_FOUND = "Объект не найден";


//    @ExceptionHandler(NotFoundException.class)
//    public ResponseEntity<Object> handleResourceNotFoundException(ChangeSetPersister.NotFoundException ex, WebRequest request) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("message", "Resource not found");
//
//        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
//    }


    // Обработка всех других исключений
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
//        Map<String, Object> body = new HashMap<>();
//        body.put("timestamp", LocalDateTime.now());
//        body.put("message", "An error occurred");
//
//        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    public ResponseEntity<String> handleIllegalArgument(Exception ex, WebRequest request) {
//        String[] q = request.getParameterValues("ticket");
//        System.out.println(Arrays.toString(q));
//        return ResponseEntity.badRequest().body(ex.getMessage());
//    }


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
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Object[] args = new Object[]{ex.getPropertyName(), ex.getValue()};
        CustomErrorResponse body = new CustomErrorResponse(BAD_REQUEST, "Не получилось конвертировать " + args[0] + " с значением: " + args[1], getFullURL());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }



    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<String> customNotNullErrors = new ArrayList<>();
        List<String> errors = new ArrayList<>();


        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            if ("CustomNotNull".equals(error.getCode())) {
                customNotNullErrors.add(error.getField() + ": " + error.getDefaultMessage());
            } else {
                errors.add(error.getField() + ": " + error.getDefaultMessage());
            }
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            if ("CustomNotNull".equals(error.getCode())) {
                customNotNullErrors.add(error.getObjectName() + ": " + error.getDefaultMessage());
            } else {
                errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
            }
        }


        ErrorResponseArray errorResponseArray;
        if (!customNotNullErrors.isEmpty())
            errorResponseArray =
                    new ErrorResponseArray(BAD_REQUEST, customNotNullErrors, getFullURL());
        else
            errorResponseArray =
                    new ErrorResponseArray(UNPROCESSABLE_ENTITY, errors, getFullURL());

        return this.handleExceptionInternal(ex, errorResponseArray, headers, status, request);
    }



    //TODO: для refundable надо уточнить какое поле не читается
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ProblemDetail body = this.createProblemDetail(ex, status, ex.getCause().getLocalizedMessage(), (String) null, (Object[]) null, request);
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Object> handleInvalidParameterException(Exception ex) {
        CustomErrorResponse body = new CustomErrorResponse(UNPROCESSABLE_ENTITY, ex.getMessage(), getFullURL());
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(NoFieldException.class)
    public ResponseEntity<Object> handleNoFieldException(Exception ex) {
        CustomErrorResponse body = new CustomErrorResponse(BAD_REQUEST, "Поля " + ex.getMessage() + " нет.", getFullURL());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }



    @ExceptionHandler(NoFilterMethodException.class)
    public ResponseEntity<Object> handleNoFilterMethodException(Exception ex) {
        CustomErrorResponse body = new CustomErrorResponse(BAD_REQUEST, "Неверное условие фильтрации: " + ex.getMessage(), getFullURL());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoSortMethodException.class)
    public ResponseEntity<Object> handleNoSortMethodException(Exception ex) {
        CustomErrorResponse body = new CustomErrorResponse(BAD_REQUEST, "Метода сортировки " + ex.getMessage() + " нет.", getFullURL());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<Object> handleTicketNotFoundException(Exception ex) {
        CustomErrorResponse body = new CustomErrorResponse(NOT_FOUND, ex.getMessage(), getFullURL());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        CustomErrorResponse body = new CustomErrorResponse(INTERNAL_SERVER_ERROR, "Произошла внутренняя ошибка на сервере." + ex.getCause().getLocalizedMessage(), getFullURL());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    public String getFullURL() {
        HttpServletRequest servletRequest = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        StringBuilder url;
        url = new StringBuilder(servletRequest.getRequestURL().toString());

        String queryString = servletRequest.getQueryString();
        if (queryString != null) {
            url.append("?").append(queryString);
        }
        return url.toString();
    }
}
