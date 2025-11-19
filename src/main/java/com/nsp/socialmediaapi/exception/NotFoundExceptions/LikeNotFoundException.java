package com.nsp.socialmediaapi.exception.NotFoundExceptions;

import com.nsp.socialmediaapi.exception.BaseException.ResourceNotFoundException;

public class LikeNotFoundException extends ResourceNotFoundException {
    public LikeNotFoundException() {
        super("like not found");
    }
}
