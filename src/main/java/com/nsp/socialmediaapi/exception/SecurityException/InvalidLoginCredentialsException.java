package com.nsp.socialmediaapi.exception.SecurityException;

import com.nsp.socialmediaapi.exception.BaseException.InvalidCredentialsException;

public class InvalidLoginCredentialsException extends InvalidCredentialsException {
    public InvalidLoginCredentialsException() {
        super("Invalid username or password");
    }
}
