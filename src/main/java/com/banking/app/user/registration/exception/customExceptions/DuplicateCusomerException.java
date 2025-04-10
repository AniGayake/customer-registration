package com.banking.app.user.registration.exception.customExceptions;

public class DuplicateCusomerException extends RuntimeException{
    public DuplicateCusomerException(String panNumber){
        super(panNumber);
    }
}
