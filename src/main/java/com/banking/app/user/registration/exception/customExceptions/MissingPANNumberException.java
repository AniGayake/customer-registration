package com.banking.app.user.registration.exception.customExceptions;

public class MissingPANNumberException extends RuntimeException{
    public MissingPANNumberException(String message){
        super(message);
    }
}
