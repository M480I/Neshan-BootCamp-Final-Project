package com.etaxi.core.exception;

public class HasActiveOrderException extends RuntimeException {
    public HasActiveOrderException(String message) {
        super(message);
    }
}
