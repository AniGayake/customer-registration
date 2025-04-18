package com.banking.app.user.registration.exception.customExceptions;

public class KYCRegistrationException extends RuntimeException{
    public KYCRegistrationException(String message) {
        super(message);
    }
}
