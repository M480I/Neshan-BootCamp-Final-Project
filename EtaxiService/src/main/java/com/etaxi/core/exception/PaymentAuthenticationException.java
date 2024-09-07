package com.etaxi.core.exception;

public class PaymentAuthenticationException extends RuntimeException {
    public PaymentAuthenticationException(String message) {
        super(message);
    }
}
