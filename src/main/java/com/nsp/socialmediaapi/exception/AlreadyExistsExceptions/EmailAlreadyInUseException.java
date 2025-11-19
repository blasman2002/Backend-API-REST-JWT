package com.nsp.socialmediaapi.exception.AlreadyExistsExceptions;

import com.nsp.socialmediaapi.exception.BaseException.ResourceAlreadyExistsException;

public class EmailAlreadyInUseException extends ResourceAlreadyExistsException {
    public EmailAlreadyInUseException() {
        super("email Already In Use");
    }
}
