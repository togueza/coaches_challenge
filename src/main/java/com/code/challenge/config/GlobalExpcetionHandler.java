package com.code.challenge.config;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;


@ControllerAdvice
public class GlobalExpcetionHandler {

    /**
     * <p>The only constraint violation exceptions in application can be thrown related to Base64</p>
     */
    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Not a Base64 encoded parameter")
    @ExceptionHandler(ConstraintViolationException.class)
    public void handleConstraintViolationException(){}

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public void handleEntityNotFoundException(){}
}
