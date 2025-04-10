package com.banking.app.user.registration.web;

import com.banking.app.user.registration.bo.Address;
import com.banking.app.user.registration.bo.ContactInformation;
import com.banking.app.user.registration.bo.Customer;
import com.banking.app.user.registration.dto.AddressRequest;
import com.banking.app.user.registration.dto.ContactInformationRequest;
import com.banking.app.user.registration.dto.CustomerRequest;
import com.banking.app.user.registration.exception.customExceptions.DuplicateCusomerException;
import com.banking.app.user.registration.service.AddressService;
import com.banking.app.user.registration.service.ContactInformationService;
import com.banking.app.user.registration.service.CustomerService;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;
    private final ContactInformationService contactInformationService;
    private final AddressService addressService;

    @Autowired
    public CustomerController(final CustomerService customerService,final ContactInformationService contactInformationService,final AddressService addressService){
        this.customerService= customerService;
        this.contactInformationService= contactInformationService;
        this.addressService= addressService;
    }
    @PostMapping("/personal-info")
    public ResponseEntity<Customer> registerCustomer(final @RequestBody CustomerRequest customerRequest) throws DuplicateCusomerException {
        LOGGER.info("Entering registerCustomer to register customer with details {} ",customerRequest);
        Customer registeredCustomer = customerService.registerCustomer(customerRequest);
        LOGGER.info("Customer registered with details as {} ",registeredCustomer);
        return ResponseEntity.status(HttpStatus.CREATED).body(registeredCustomer);
    }

    @PostMapping("/contact-info")
    public ResponseEntity<ContactInformation> registerContactInformation(final @RequestBody ContactInformationRequest contactInformationRequest){
        LOGGER.info("Entering registerContactInformation to register contact information with details {}",contactInformationRequest);
        ContactInformation contactInformation = contactInformationService.registerContactInformation(contactInformationRequest);
        LOGGER.info("Customer contact details registered with details as {} ",contactInformation);
        return ResponseEntity.status(HttpStatus.CREATED).body(contactInformation);
    }

    @PostMapping("/address-info")
    public ResponseEntity<Address> registerAddress(final @RequestBody AddressRequest addressRequest){
        LOGGER.info("Entering registerAddress to register contact information with details {}",addressRequest);
        Address address = addressService.registerAddress(addressRequest);
        LOGGER.info("Customer Address details registered with details as {} ",address);
        return ResponseEntity.status(HttpStatus.CREATED).body(address);

    }

}
