package com.bank.antifraud.exception;

public class EmptyReturnedValueException extends RuntimeException {
    public EmptyReturnedValueException(String message) {
        super(message);
    }
}
