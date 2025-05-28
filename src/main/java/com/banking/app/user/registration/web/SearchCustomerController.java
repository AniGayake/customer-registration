package com.banking.app.user.registration.web;

import com.banking.app.user.registration.dto.response.SearchCustomerInSystemResponse;
import com.banking.app.user.registration.service.SearchCustomerService;
import org.hibernate.QueryParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/customer")
public class SearchCustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchCustomerController.class);
    private final SearchCustomerService searchCustomerService;

    @Autowired
    public SearchCustomerController(final SearchCustomerService searchCustomerService ){
        this.searchCustomerService= searchCustomerService;
    }

    @GetMapping("/searchCustomerInSystem")
    public ResponseEntity<SearchCustomerInSystemResponse> checkIfUserAlreadyExistsInSystem(@RequestParam(value = "aadharNumber",required = false) String aadharNumber,
                                                           @RequestParam(value = "panNumber",required = false) String panNumber,
                                                           @RequestParam(value = "voterId",required = false) String voterId,
                                                           @RequestParam(value = "passportNumber",required = false) String passportNumber){
        LOGGER.info("In checkIfUserAlreadyExistsInSystem(), Searching the customer");
        checkForAtLeastOneRequestParameter(aadharNumber,panNumber,voterId,passportNumber);
       SearchCustomerInSystemResponse customer=  searchCustomerService.searchCustomer(aadharNumber,panNumber,voterId,passportNumber);
        LOGGER.info("Exiting checkIfUserAlreadyExistsInSystem ()");
        if (customer.getDoesExists().equals(false)){
            LOGGER.error("Customer not found in the System ");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(customer);
        }else {
            LOGGER.info("Customer found, returning the response");
            return ResponseEntity.status(HttpStatus.OK).body(customer);
        }
    }

    private void checkForAtLeastOneRequestParameter(String aadharNumber, String panNumber, String voterId, String passportNumber) {
        if(Objects.isNull(aadharNumber)&&Objects.isNull(panNumber)&& Objects.isNull(voterId)&&Objects.isNull(passportNumber)){
            LOGGER.error("No Query parameter provided in the request");
            throw new QueryParameterException("Please pass Aadhar or PAN or Voter or Passport Number as Query Parameter");
        }
    }


}
