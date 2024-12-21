package org.example.ticketservice.exception.tools;

import com.fasterxml.jackson.databind.JsonMappingException;
import jakarta.servlet.http.HttpServletRequest;
import org.example.ticketservice.exception.*;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    String UNPROCESSABLE_ENTITY = "Ошибка валидации данных";
    String BAD_REQUEST = "Некорректные данные";
    String INTERNAL_SERVER_ERROR = "Внутренняя ошибка сервера";
    String NOT_FOUND = "Объект не найден";


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
        Throwable cause = ex.getCause();
        if (cause instanceof JsonMappingException) {
            String message = cause.getMessage();
            if (message.contains("Numeric value")) {
                String me = ex.getCause().getLocalizedMessage().split("\n")[0];
                CustomErrorResponse body = new CustomErrorResponse(BAD_REQUEST, "Значение превышает возможный диапазон: " + me, getFullURL());
                return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
            }
        } else {
            CustomErrorResponse body = new CustomErrorResponse(BAD_REQUEST, "Не получается прочесть данные: " + ex.getCause().getLocalizedMessage(), getFullURL());
            return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<Object> handleInvalidParameterException(Exception ex) {
        CustomErrorResponse body = new CustomErrorResponse(UNPROCESSABLE_ENTITY, ex.getMessage(), getFullURL());
        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(BadPersonException.class)
    public ResponseEntity<Object> handleNoPersonException(Exception ex) {
        BadPersonException badPersonException = (BadPersonException) ex;
        ErrorResponseArray body = new ErrorResponseArray(BAD_REQUEST, badPersonException.getErrors(), getFullURL());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
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

    @ExceptionHandler(NoValueException.class)
    public ResponseEntity<Object> handleNoValueException(Exception ex) {
        CustomErrorResponse body = new CustomErrorResponse(BAD_REQUEST, "Нет значения для фильтрации", getFullURL());
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

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> handleRuntimeEx(Exception ex) {
        CustomErrorResponse body = new CustomErrorResponse(INTERNAL_SERVER_ERROR, ex.getMessage(), getFullURL());
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllExceptions(Exception ex) {
        CustomErrorResponse body = new CustomErrorResponse(INTERNAL_SERVER_ERROR, "Произошла внутренняя ошибка на сервере." + ex.getCause(), getFullURL());
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
