package com.nsp.socialmediaapi.exception.InvalidArgumentExceptions;

import com.nsp.socialmediaapi.exception.BaseException.InvalidArgumentException;

public class CommentDoesNotBelongToPostException extends InvalidArgumentException {
    public CommentDoesNotBelongToPostException() {
        super("comment does not belong to this post");
    }
}
