package com.nsp.socialmediaapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class ExceptionResponseBuilder {

    public ResponseEntity<ExceptionResponse> buildResponse(Exception e, HttpStatus status, HttpServletRequest request) {
        List<FieldError> responseErrors;

        if (e instanceof MethodArgumentNotValidException mae) {
            responseErrors = mae.getBindingResult()
                    .getFieldErrors()
                    .stream()
                    .map(err -> new FieldError(err.getField(), err.getDefaultMessage()))
                    .toList();
        } else {
            responseErrors = List.of(
                    new FieldError(null, e.getMessage())
            );
        }

        ExceptionResponse response = new ExceptionResponse(
                status.value(),
                status.getReasonPhrase(),
                request.getRequestURI(),
                LocalDateTime.now(),
                responseErrors
        );

        return new ResponseEntity<>(response, status);
    }

}
