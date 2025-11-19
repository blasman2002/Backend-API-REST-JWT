package com.nsp.socialmediaapi.exception.AlreadyExistsExceptions;

import com.nsp.socialmediaapi.exception.BaseException.ResourceAlreadyExistsException;

public class UsernameAlreadyInUseException extends ResourceAlreadyExistsException {
    public UsernameAlreadyInUseException() {
        super("username already in use");
    }
}
