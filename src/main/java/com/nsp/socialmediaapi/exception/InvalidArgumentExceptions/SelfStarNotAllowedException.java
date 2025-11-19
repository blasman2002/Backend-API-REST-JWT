package com.nsp.socialmediaapi.exception.InvalidArgumentExceptions;

import com.nsp.socialmediaapi.exception.BaseException.InvalidArgumentException;

public class SelfStarNotAllowedException extends InvalidArgumentException {
    public SelfStarNotAllowedException() {
        super("cant star logged user");
    }
}
