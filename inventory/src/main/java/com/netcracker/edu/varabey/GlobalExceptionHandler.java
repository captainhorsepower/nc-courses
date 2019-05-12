package com.netcracker.edu.varabey;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFoundConflict(EntityNotFoundException ex) {
        return ex.getClass().getSimpleName() + ": " + ex.getMessage();
    }

    @ExceptionHandler({EmptyResultDataAccessException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleRepositoryConflict(EmptyResultDataAccessException ex) {
        return ex.getClass().getSimpleName() + ": " + ex.getMessage();
    }

    @ExceptionHandler({IllegalArgumentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidRequestConflict(IllegalArgumentException ex) {
        return ex.getClass().getSimpleName() + ": " + ex.getMessage();
    }
}
