package com.nsp.redsocial.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e, WebRequest r) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("timestamp", new Date());
        errors.put("exception", e.getMessage());
        errors.put("path", r.getDescription(false));

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }
}
