package com.nsp.socialmediaapi.exception.InvalidArgumentExceptions;

import com.nsp.socialmediaapi.exception.BaseException.InvalidArgumentException;

public class CommentDoesNotBelongToUserException extends InvalidArgumentException {
    public CommentDoesNotBelongToUserException() {
        super("comment does not belong to this user");
    }
}
