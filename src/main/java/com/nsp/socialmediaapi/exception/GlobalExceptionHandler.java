package com.nsp.socialmediaapi.exception;

import com.nsp.socialmediaapi.exception.BaseException.InvalidArgumentException;
import com.nsp.socialmediaapi.exception.BaseException.ResourceAlreadyExistsException;
import com.nsp.socialmediaapi.exception.BaseException.ResourceNotFoundException;
import com.nsp.socialmediaapi.exception.BaseException.TokenException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        return buildExceptionResponse(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException e,
            HttpServletRequest request
    ) {
        return buildExceptionResponse(e, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public  ResponseEntity<ExceptionResponse> handleInvalidArgumentException(
            InvalidArgumentException e,
            HttpServletRequest request
    ) {
        return buildExceptionResponse(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(
            ResourceNotFoundException e,
            HttpServletRequest request
    ) {
        return buildExceptionResponse(e, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ExceptionResponse> handleTokenException(
            TokenException e,
            HttpServletRequest request
    ) {
        return buildExceptionResponse(e, HttpStatus.UNAUTHORIZED, request);
    }

    private ResponseEntity<ExceptionResponse> buildExceptionResponse(Exception e,HttpStatus status, HttpServletRequest request) {
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
