package com.nsp.socialmediaapi.exception.NotFoundExceptions;

import com.nsp.socialmediaapi.exception.BaseException.ResourceNotFoundException;

public class UserNotFoundException extends ResourceNotFoundException {
    public UserNotFoundException() {
        super("user not found");
    }
}
