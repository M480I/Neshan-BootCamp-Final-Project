package com.etaxi.core.exception;

public class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException (String message) {
        super(message);
    }
}
