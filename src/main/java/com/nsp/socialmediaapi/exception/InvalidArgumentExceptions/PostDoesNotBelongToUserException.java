package com.nsp.socialmediaapi.exception.InvalidArgumentExceptions;

import com.nsp.socialmediaapi.exception.BaseException.InvalidArgumentException;

public class PostDoesNotBelongToUserException extends InvalidArgumentException {
    public PostDoesNotBelongToUserException() {
        super("post does not belong to this user");
    }
}
