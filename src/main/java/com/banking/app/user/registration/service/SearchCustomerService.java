package com.banking.app.user.registration.service;

import com.banking.app.user.registration.bo.Customer;
import com.banking.app.user.registration.bo.CustomerIdentityProof;
import com.banking.app.user.registration.dto.response.SearchCustomerInSystemResponse;
import com.banking.app.user.registration.repository.CustomerRepository;
import com.banking.app.user.registration.repository.IdentityProofInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
@Service
public class SearchCustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SearchCustomerService.class);

    private IdentityProofInfoRepository identityProofInfoRepository;
    private CustomerRepository customerRepository;
    @Autowired
    public SearchCustomerService(final IdentityProofInfoRepository identityProofInfoRepository,final CustomerRepository customerRepository){
        this.identityProofInfoRepository = identityProofInfoRepository;
        this.customerRepository= customerRepository;
    }

    public SearchCustomerInSystemResponse searchCustomer(String aadharNumber, String panNumber, String voterId, String passportNumber) {
        SearchCustomerInSystemResponse searchCustomerInSystemResponse= new SearchCustomerInSystemResponse();
        Optional<CustomerIdentityProof> customer;
        if (Objects.nonNull(aadharNumber)){
            LOGGER.info("Searching customer with Aadhar Number {}",aadharNumber);
           customer= checkCustomerByAadharNumber(aadharNumber);
        } else if (Objects.nonNull(panNumber)&&!panNumber.isBlank()) {
            LOGGER.info("Searching customer with PAN Number {}",panNumber);
            customer= checkCustomerByPanNumber(panNumber);
        }else if (Objects.nonNull(voterId)&&!voterId.isBlank()){
            LOGGER.info("Searching customer with VoterId Number {}",voterId);
            customer= checkCustomerByVoterId(voterId);

        } else if (Objects.nonNull(passportNumber)&&!passportNumber.isBlank()) {
            LOGGER.info("Searching customer with Passport Number {}",passportNumber);
            customer=checkCustomerByPassportNumber(passportNumber);
        }else {
            customer= Optional.empty();
        }

        if(customer.isEmpty()){
            searchCustomerInSystemResponse.setDoesExists(false);
        }else {
        LOGGER.info("Returning customer from SearchCustomerService");
        if(customer.isPresent()){
            Long customerId = customer.get().getCustomerId();
            Optional<Customer> customerPersonalDetails = customerRepository.findById(customerId);
            if(customerPersonalDetails.isPresent()){
                searchCustomerInSystemResponse.setDoesExists(true);
                searchCustomerInSystemResponse.setFirstName(customerPersonalDetails.get().getFirstName());
                searchCustomerInSystemResponse.setLastName(customerPersonalDetails.get().getLastName());
                searchCustomerInSystemResponse.setCustomerId(customerId);
                searchCustomerInSystemResponse.setAadharNumber(customer.get().getAadharNumber());
                searchCustomerInSystemResponse.setPanNumber(customer.get().getPanNumber());
                searchCustomerInSystemResponse.setPassportNumber(customer.get().getPassportNumber());
                searchCustomerInSystemResponse.setVoterIdNumber(customer.get().getVoterIdNumber());
            }
        }
        }
        return searchCustomerInSystemResponse;
    }


    private Optional<CustomerIdentityProof> checkCustomerByAadharNumber(String aadharNumber) {
        return identityProofInfoRepository.findCustomerByAadharNumber(aadharNumber);
    }

    private Optional<CustomerIdentityProof> checkCustomerByPassportNumber(String passportNumber) {
        return identityProofInfoRepository.findCustomerByPassportNumber(passportNumber);
    }

    private Optional<CustomerIdentityProof> checkCustomerByVoterId(String voterId) {
       return identityProofInfoRepository.findCustomerByVoterIdNumber(voterId);
    }

    public Optional<CustomerIdentityProof> checkCustomerByPanNumber(String panNumber) {
        return identityProofInfoRepository.findCustomerByPanNumber(panNumber);

    }
}
