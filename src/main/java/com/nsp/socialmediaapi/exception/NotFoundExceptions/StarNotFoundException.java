package com.nsp.socialmediaapi.exception.NotFoundExceptions;

import com.nsp.socialmediaapi.exception.BaseException.ResourceNotFoundException;

public class StarNotFoundException extends ResourceNotFoundException {
    public StarNotFoundException() {
        super("star not found");
    }
}
