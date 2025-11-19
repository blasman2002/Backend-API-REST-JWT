package com.nsp.socialmediaapi.exception.AlreadyExistsExceptions;

import com.nsp.socialmediaapi.exception.BaseException.ResourceAlreadyExistsException;

public class StarAlreadyExistsException extends ResourceAlreadyExistsException {
    public StarAlreadyExistsException() {
        super("star already exists");
    }
}
