package com.nsp.socialmediaapi.exception.SecurityException;

public class UserDisabledException extends RuntimeException {
    public UserDisabledException() {
        super("User is disabled");
    }
}
