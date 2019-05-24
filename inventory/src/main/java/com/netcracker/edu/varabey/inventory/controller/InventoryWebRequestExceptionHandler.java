package com.netcracker.edu.varabey.inventory.controller;

import com.netcracker.edu.varabey.inventory.exceptions.HttpStatusException;
import com.netcracker.edu.varabey.inventory.util.aop.beanannotation.Logged;
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
public class InventoryWebRequestExceptionHandler extends ResponseEntityExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(InventoryWebRequestExceptionHandler.class);

    public String buildLogMessage(Throwable ex) {
        final StringBuilder messageBuilder = new StringBuilder();
        final String separator = " -> ";
        while (ex != null) {
            messageBuilder.append(ex.getClass().getSimpleName())
                    .append(": ")
                    .append((ex.getMessage() == null) ? "..." : ex.getMessage())
                    .append(separator);
            ex = ex.getCause();
        }
        messageBuilder.setLength(messageBuilder.length() - separator.length());
        return messageBuilder.toString();
    }
    
    @Logged(messageBefore = "Handling known exception...", messageAfter = "Done, response with error message sent.", level = LogLevel.ERROR)
    @ExceptionHandler({HttpStatusException.class})
    public ResponseEntity handleInventoryConflict(HttpStatusException ex, WebRequest request) {
        logger.error(buildLogMessage(ex));
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), ex.getHttpStatus(), request);
    }


    @Logged(messageBefore = "ENCOUNTERED UNEXPECTED EXCEPTION!!!!!", messageAfter = "Done, response with error message sent.", level = LogLevel.FATAL)
    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity handleUnknownConflict(RuntimeException ex, WebRequest request) {
        logger.error(buildLogMessage(ex));
        return handleExceptionInternal(ex, ex.getMessage(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
