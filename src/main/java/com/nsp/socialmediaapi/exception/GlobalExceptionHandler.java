package com.nsp.socialmediaapi.exception;

import com.nsp.socialmediaapi.exception.BaseException.InvalidArgumentException;
import com.nsp.socialmediaapi.exception.BaseException.ResourceAlreadyExistsException;
import com.nsp.socialmediaapi.exception.BaseException.ResourceNotFoundException;
import com.nsp.socialmediaapi.exception.BaseException.TokenException;
import com.nsp.socialmediaapi.util.ExceptionResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    private final ExceptionResponseBuilder responseBuilder;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e,
            HttpServletRequest request
    ) {
        return responseBuilder.buildResponse(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleResourceAlreadyExistsException(
            ResourceAlreadyExistsException e,
            HttpServletRequest request
    ) {
        return responseBuilder.buildResponse(e, HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(InvalidArgumentException.class)
    public  ResponseEntity<ExceptionResponse> handleInvalidArgumentException(
            InvalidArgumentException e,
            HttpServletRequest request
    ) {
        return responseBuilder.buildResponse(e, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFoundException(
            ResourceNotFoundException e,
            HttpServletRequest request
    ) {
        return responseBuilder.buildResponse(e, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ExceptionResponse> handleTokenException(
            TokenException e,
            HttpServletRequest request
    ) {
        return responseBuilder.buildResponse(e, HttpStatus.UNAUTHORIZED, request);
    }

}
