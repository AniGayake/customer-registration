package com.banking.app.user.registration.exception.customExceptions;

public class BlankInputException extends RuntimeException{
    public BlankInputException(String message) {
        super(message);
    }
}
