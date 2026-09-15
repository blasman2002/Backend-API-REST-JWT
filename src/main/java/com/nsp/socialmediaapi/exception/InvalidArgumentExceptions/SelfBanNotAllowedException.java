package com.nsp.socialmediaapi.exception.InvalidArgumentExceptions;

import com.nsp.socialmediaapi.exception.BaseException.InvalidArgumentException;

public class SelfBanNotAllowedException extends InvalidArgumentException {
    public SelfBanNotAllowedException() {
        super("Can't ban logged user");
    }
}
