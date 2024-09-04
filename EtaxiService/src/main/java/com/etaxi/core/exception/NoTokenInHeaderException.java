package com.etaxi.core.exception;

public class NoTokenInHeaderException extends RuntimeException {
    public NoTokenInHeaderException (String message) {
        super(message);
    }
}
