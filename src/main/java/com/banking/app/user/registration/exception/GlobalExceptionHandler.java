package com.banking.app.user.registration.exception;

import com.banking.app.user.registration.exception.customExceptions.*;
import com.banking.app.user.registration.exception.entity.ErrorMessage;
import org.hibernate.QueryParameterException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(QueryParameterException.class)
    public ResponseEntity<ErrorMessage> handleNoQueryParameterException(QueryParameterException queryParameterException){
        ErrorMessage errorMessage = new ErrorMessage("Invalid Query parameters",HttpStatus.BAD_REQUEST.value(),queryParameterException.getMessage());

        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateCusomerException.class)
    public ResponseEntity<ErrorMessage> handleDuplicateCustomerException(final DuplicateCusomerException exception){
        ErrorMessage errorMessage = new ErrorMessage("Duplicate Customer",HttpStatus.CONTINUE.value(), "Customer with PAN "+exception.getMessage()+ " already exits");
        return new ResponseEntity<>(errorMessage,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CustomerRegistrationException.class)
    public ResponseEntity<ErrorMessage> handleCustomerRegistrationException(final CustomerRegistrationException exception){
        ErrorMessage errorMessage = new ErrorMessage("Error Registering Customer ",HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(MissingPANNumberException.class)
    public ResponseEntity<ErrorMessage> handleMissingPANNumberException(final MissingPANNumberException exception){
        ErrorMessage errorMessage = new ErrorMessage("Missing mandatory field",HttpStatus.BAD_REQUEST.value(), exception.getMessage());
        return new ResponseEntity<>(errorMessage,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> handleCustomSQLException(final DataIntegrityViolationException dataIntegrityViolationException){
        String message = dataIntegrityViolationException.getRootCause().getMessage();
        String errorMessageVal= "Duplicate value";
        if(message.contains("contact_info.email")){
            errorMessageVal = "Email already exists";
        } else if (message.contains("contact_info.phone_number")) {
            errorMessageVal = "Mobile number already exists.";
        }
        ErrorMessage errorMessage = new ErrorMessage("Database Integrity Issue ",HttpStatus.CONFLICT.value(), errorMessageVal);
        return new ResponseEntity<>(errorMessage,HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ContactRegistrationException.class)
    public ResponseEntity<ErrorMessage> handleContactRegistrationException(final ContactRegistrationException exception){
        ErrorMessage errorMessage = new ErrorMessage("Contact Information Registration Error",HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(KYCRegistrationException.class)
    public ResponseEntity<ErrorMessage> handleKYCRegistrationException(final KYCRegistrationException exception){
        ErrorMessage errorMessage = new ErrorMessage("KYC Registration Error",HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
        return new ResponseEntity<>(errorMessage,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleCustomerNotFoundException(final CustomerNotFoundException exception){
        ErrorMessage errorMessage = new ErrorMessage("NOT FOUND",HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(BlankInputException.class)
    public ResponseEntity<ErrorMessage> handleBlankInputException(final BlankInputException exception){
        ErrorMessage errorMessage = new ErrorMessage("BLANK INPUT",HttpStatus.NOT_FOUND.value(), exception.getMessage());
        return new ResponseEntity<>(errorMessage,HttpStatus.NOT_FOUND);
    }
}
