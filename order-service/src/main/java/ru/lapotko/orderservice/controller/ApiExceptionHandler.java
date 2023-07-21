package ru.lapotko.orderservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import ru.lapotko.orderservice.dto.ErrorDetails;
import ru.lapotko.orderservice.exception.OrderApiException;

import java.util.Date;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(OrderApiException.class)
    public ResponseEntity<?> orderApiExceptionHandler(Exception exception, WebRequest request) {
        return new ResponseEntity<>(new ErrorDetails(new Date(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
