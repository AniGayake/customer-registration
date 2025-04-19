package com.banking.app.user.registration.web;

import com.banking.app.user.registration.bo.*;
import com.banking.app.user.registration.bo.SavingAccount;
import com.banking.app.user.registration.dto.request.*;
import com.banking.app.user.registration.dto.response.LoginCreationResponse;
import com.banking.app.user.registration.exception.customExceptions.DuplicateCusomerException;
import com.banking.app.user.registration.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

    private final CustomerService customerService;
    private final ContactInformationService contactInformationService;
    private final AddressService addressService;
    private final KYCService kycService;
    private final LoginService loginService;
    private final SavingAccountService savingAccountService;

    @Autowired
    public CustomerController(final CustomerService customerService,final ContactInformationService contactInformationService,
                              final AddressService addressService,final KYCService kycService,final LoginService loginCreationService,
                              final SavingAccountService savingAccountService){
        this.customerService= customerService;
        this.contactInformationService= contactInformationService;
        this.addressService= addressService;
        this.kycService = kycService;
        this.loginService= loginCreationService;
        this.savingAccountService= savingAccountService;
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

    @PostMapping("/kyc-info")
    public ResponseEntity<KYCDetails> registerCustomerKYC(final @RequestBody KYCDetailsRequest kycDetailsRequest){
        LOGGER.info("Entering into registerCustomerKYC to register KYC details with details {}",kycDetailsRequest);
        KYCDetails kycDetails = kycService.customerKYC(kycDetailsRequest);
        LOGGER.info("Exiting into registerCustomerKYC to register KYC details with details {}",kycDetails);

        return ResponseEntity.status(HttpStatus.CREATED).body(kycDetails);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginCreationResponse> createLoginDetailsForCustomer(final @RequestBody LoginRequestCreation loginRequestCreation){
        LOGGER.info("Entering createLoginDetailsForCustomer with customerId {}",loginRequestCreation.getCustomerId());
        loginService.createLoginDetailsForNewCustomer(loginRequestCreation.getCustomerId());
        LoginCreationResponse loginCreationResponse = new LoginCreationResponse();
        loginCreationResponse.setCustomerId(loginCreationResponse.getCustomerId());
        loginCreationResponse.setMessage("Login Id created Successfully");
        LOGGER.info("Login Id created Successfully for {}",loginRequestCreation.getCustomerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(loginCreationResponse);
    }

    @PostMapping("/account")
    public ResponseEntity<SavingAccount> createSavingAccount(final @RequestBody SavingAccountRequest savingAccountRequest){
        SavingAccount savingAccount = savingAccountService.createAccount(savingAccountRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savingAccount);

    }
}
