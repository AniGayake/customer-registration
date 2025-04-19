package com.banking.app.user.registration.service;

import com.banking.app.user.registration.bo.ContactInformation;
import com.banking.app.user.registration.dto.request.ContactInformationRequest;
import com.banking.app.user.registration.exception.customExceptions.ContactRegistrationException;
import com.banking.app.user.registration.repository.ContactInformationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Objects;

@Service
public class ContactInformationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContactInformationService.class);
    private final ContactInformationRepository contactInformationRepository;

    @Autowired
    public ContactInformationService(final ContactInformationRepository contactInformationRepository) {
        this.contactInformationRepository = contactInformationRepository;
    }

    public ContactInformation registerContactInformation(@RequestBody final ContactInformationRequest contactInformationRequest){
        LOGGER.info("Entering registerContactInformation");
        ContactInformation contactInformation = populateContactInformation(contactInformationRequest);
        ContactInformation registeredContactInformation = contactInformationRepository.save(contactInformation);
        if(Objects.nonNull(registeredContactInformation.getContactId())){
            LOGGER.info("Exiting registerContactInformation");
            return registeredContactInformation;
        }else {
            throw new ContactRegistrationException("Error Registering Contact Information");
        }
    }

    private static ContactInformation populateContactInformation(ContactInformationRequest contactInformationRequest) {
        ContactInformation contactInformation = new ContactInformation();
        contactInformation.setCustomerId(contactInformationRequest.getCustomerId());
        contactInformation.setEmail(contactInformationRequest.getEmailId());
        contactInformation.setPhoneNumber(contactInformationRequest.getPhoneNumber());
        contactInformation.setAlternatePhoneNumber(contactInformationRequest.getAlternatePhoneNumber());
        return contactInformation;
    }
}
