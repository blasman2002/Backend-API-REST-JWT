package com.nsp.socialmediaapi.exception.NotFoundExceptions;

public class CommentNotFoundException extends RuntimeException {
    public CommentNotFoundException() {
        super("comment not found");
    }
}
