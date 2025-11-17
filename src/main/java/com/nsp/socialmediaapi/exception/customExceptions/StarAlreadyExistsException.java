package com.nsp.socialmediaapi.exception.customExceptions;

public class StarAlreadyExistsException extends RuntimeException {
    public StarAlreadyExistsException(String message) {
        super(message);
    }
}
