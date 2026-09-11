package com.nsp.socialmediaapi.exception.SecurityException;

import com.nsp.socialmediaapi.exception.BaseException.InvalidCredentialsException;

public class InvalidRefreshTokenException extends InvalidCredentialsException {
    public InvalidRefreshTokenException() {
        super("invalid refresh token");
    }
}
