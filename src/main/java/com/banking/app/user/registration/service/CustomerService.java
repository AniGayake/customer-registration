package com.banking.app.user.registration.service;

import com.banking.app.user.registration.bo.Customer;
import com.banking.app.user.registration.dto.CustomerRequest;
import com.banking.app.user.registration.exception.customExceptions.CustomerRegistrationException;
import com.banking.app.user.registration.exception.customExceptions.DuplicateCusomerException;
import com.banking.app.user.registration.exception.customExceptions.MissingPANNumberException;
import com.banking.app.user.registration.repository.CustomerRepository;
import com.banking.app.user.registration.utils.GenerateCustomerId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository customerRepository;
    private final GenerateCustomerId generateCustomerId ;
    private final SearchCustomerService searchCustomerService;

    @Autowired
    public CustomerService(final CustomerRepository customerRepository,final GenerateCustomerId generateCustomerId,final SearchCustomerService searchCustomerService){
        this.customerRepository =customerRepository;
        this.generateCustomerId= generateCustomerId;
        this.searchCustomerService= searchCustomerService;
    }
    public Customer registerCustomer(final CustomerRequest customerRequest) throws DuplicateCusomerException {
        checkMandatoryPANNumber(customerRequest);
        Customer customer = populateCustomerInformation(customerRequest);
        return registerCustomer(customerRequest, customer);
    }

    private Customer registerCustomer(CustomerRequest customerRequest, Customer customer) {

        if(!checkIfCustomerAlreadyExistsByPANNumber(customerRequest.getPanNumber())){
            Customer registeredCustomer =  customerRepository.save(customer);
            if(Objects.nonNull(registeredCustomer.getCustomerId())){
                return registeredCustomer;
            }else{
                LOGGER.error("Error Registering Customer with details {}",registeredCustomer);
                throw new CustomerRegistrationException();
            }

        }else{
            throw new DuplicateCusomerException(customerRequest.getPanNumber());
        }
    }

    private void checkMandatoryPANNumber(CustomerRequest customerRequest) {
        if(Objects.isNull(customerRequest.getPanNumber())|| customerRequest.getPanNumber().isBlank()){
            throw new MissingPANNumberException("Missing Mandatory PAN Number field");
        }
    }

    private boolean checkIfCustomerAlreadyExistsByPANNumber(String panNumber) {
        return searchCustomerService.checkCustomerByPanNumber(panNumber).isPresent();
    }

    private Customer populateCustomerInformation(CustomerRequest customerRequest) {
        Customer customer= new Customer();
        customer.setCustomerId(generateCustomerId.generateCustomerId());
       customer.setFirstName(customerRequest.getFirstName());
       customer.setLastName(customerRequest.getLastName());
       customer.setGender(customerRequest.getGender());
       customer.setNationality(customerRequest.getNationality());
       customer.setAnnualIncome(customerRequest.getAnnualIncome());
       customer.setDateOfBirth(customerRequest.getDateOfBirth());
       customer.setMaritalStatus(customerRequest.getMaritalStatus());
       customer.setOccupation(customerRequest.getOccupation());
       customer.setCreatedAt(LocalDateTime.now());
       return customer;

    }

}
