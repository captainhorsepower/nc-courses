package com.netcracker.edu.varabey.catalog.web.controller;

import com.netcracker.edu.varabey.catalog.springutils.beanannotation.Logged;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.CategoryException;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.HttpStatusException;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.OfferException;
import com.netcracker.edu.varabey.catalog.data.validation.exceptions.TagException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CatalogControllerExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(CatalogControllerExceptionHandler.class);

    @ExceptionHandler({OfferException.class, CategoryException.class, TagException.class})
    public ResponseEntity handleKnownConflict(HttpStatusException ex, WebRequest request) {
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), ex.getHttpStatus(), request);
    }

    @Logged(messageBefore = "Unexpected exception, use debugger for details...", messageAfter = "INTERNAL_ERROR response is sent.", level = LogLevel.FATAL)
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity handleUnhandledConflict(RuntimeException ex, WebRequest request) {
        logger.error("Unhandled exception -> {}", ex.toString());
        Throwable cause = ex.getCause();
        while (cause != null) {
            logger.error("caused by -> {}", cause.toString());
            cause = cause.getCause();
        }
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
