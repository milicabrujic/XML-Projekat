package com.main.app.exception;

public class TokenExpiredException extends IllegalArgumentException {

    private static final String MESSAGE = "TOKEN_EXPIRED";

    public TokenExpiredException() {
        super(MESSAGE);
    }
}
