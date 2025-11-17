package com.nsp.socialmediaapi.exception.handler;

import com.nsp.socialmediaapi.exception.customExceptions.LikeAlreadyExistsException;
import com.nsp.socialmediaapi.exception.customExceptions.ResourceNotFoundException;
import com.nsp.socialmediaapi.exception.customExceptions.StarAlreadyExistsException;
import com.nsp.socialmediaapi.exception.customExceptions.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("exception", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<?> handleUserAlreadyExistsException(UserAlreadyExistsException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("exception", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(LikeAlreadyExistsException.class)
    public ResponseEntity<?> handleLikeAlreadyExistsException(LikeAlreadyExistsException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("exception", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(StarAlreadyExistsException.class)
    public ResponseEntity<?> handleStarAlreadyExistsException(StarAlreadyExistsException e) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("exception", e.getMessage());

        return new ResponseEntity<>(errors, HttpStatus.CONFLICT);
    }

}
