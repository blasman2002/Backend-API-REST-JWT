package com.nsp.socialmediaapi.exception.AlreadyExistsExceptions;

import com.nsp.socialmediaapi.exception.BaseException.ResourceAlreadyExistsException;

public class LikeAlreadyExistsException extends ResourceAlreadyExistsException {
    public LikeAlreadyExistsException() {
        super("like already exists");
    }
}
