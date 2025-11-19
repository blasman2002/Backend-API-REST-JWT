package com.nsp.socialmediaapi.exception.AlreadyExistsExceptions;

import com.nsp.socialmediaapi.exception.BaseException.ResourceAlreadyExistsException;

public class UserAlreadyExistsException extends ResourceAlreadyExistsException {
    public UserAlreadyExistsException() {
        super("user already exists");
    }
}
