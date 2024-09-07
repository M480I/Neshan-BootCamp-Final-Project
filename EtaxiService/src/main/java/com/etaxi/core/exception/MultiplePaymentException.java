package com.etaxi.core.exception;

public class MultiplePaymentException extends RuntimeException {
    public MultiplePaymentException(String message) {
        super(message);
    }
}
