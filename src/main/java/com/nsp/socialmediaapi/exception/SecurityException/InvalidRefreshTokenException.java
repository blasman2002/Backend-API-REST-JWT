package com.nsp.socialmediaapi.exception.SecurityException;

import com.nsp.socialmediaapi.exception.BaseException.TokenException;

public class InvalidRefreshTokenException extends TokenException {
    public InvalidRefreshTokenException() {
        super("invalid refresh token");
    }
}
