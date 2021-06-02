package com.main.app.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("serial")
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidPasswordException extends IllegalArgumentException {

    private static final String MESSAGE = "INVALID_PASSWORD";

    public InvalidPasswordException() {
        super(MESSAGE);
    }
}


