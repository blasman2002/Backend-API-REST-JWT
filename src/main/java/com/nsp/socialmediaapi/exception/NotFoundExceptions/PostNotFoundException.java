package com.nsp.socialmediaapi.exception.NotFoundExceptions;

import com.nsp.socialmediaapi.exception.BaseException.ResourceNotFoundException;

public class PostNotFoundException extends ResourceNotFoundException {
    public PostNotFoundException() {
        super("Post not found");
    }
}
