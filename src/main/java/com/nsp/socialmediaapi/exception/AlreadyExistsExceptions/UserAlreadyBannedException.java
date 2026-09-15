package com.nsp.socialmediaapi.exception.AlreadyExistsExceptions;

import com.nsp.socialmediaapi.exception.BaseException.ResourceAlreadyExistsException;

public class UserAlreadyBannedException extends ResourceAlreadyExistsException {
    public UserAlreadyBannedException() {
        super("user is already banned");
    }
}
