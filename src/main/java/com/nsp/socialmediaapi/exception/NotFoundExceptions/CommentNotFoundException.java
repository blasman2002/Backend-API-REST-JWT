package com.nsp.socialmediaapi.exception.NotFoundExceptions;

import com.nsp.socialmediaapi.exception.BaseException.ResourceNotFoundException;

public class CommentNotFoundException extends ResourceNotFoundException {
    public CommentNotFoundException() {
        super("comment not found");
    }
}
