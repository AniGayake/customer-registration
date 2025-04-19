package com.banking.app.user.registration.service;

import com.banking.app.sms.utility.service.SMSService;
import com.banking.app.user.registration.bo.ContactInformation;
import com.banking.app.user.registration.bo.LoginDetails;
import com.banking.app.user.registration.constants.CustomerRegistrationConstants;
import com.banking.app.user.registration.repository.ContactInformationRepository;
import com.banking.app.user.registration.repository.LoginRepository;
import com.banking.app.user.registration.utils.GeneratePasswordUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    private final LoginRepository loginRepository;
    private final ContactInformationRepository contactInformationRepository;
    private final SMSService smsService;

    public LoginService(final LoginRepository loginRepository,final ContactInformationRepository contactInformationRepository
    ,final SMSService smsService) {
        this.loginRepository = loginRepository;
        this.contactInformationRepository = contactInformationRepository;
        this.smsService = smsService;
    }

    public void createLoginDetailsForNewCustomer(final Long customerId){
        LOGGER.info("Creating login details for customer with Id {}", customerId);
        LoginDetails loginDetails = populateLoginDetails(customerId);
        loginRepository.save(loginDetails);
    }

    private LoginDetails populateLoginDetails(Long customerId) {
        LoginDetails loginDetails = new LoginDetails();
        String password = generateRandomPasswordForNewCustomer();
        String hashedPassword = new BCryptPasswordEncoder().encode(password);
        loginDetails.setCustomerId(customerId);
        loginDetails.setPassword(hashedPassword);
        loginDetails.setLastLogin(LocalDateTime.now());
        loginDetails.setLastFailedLogin(null);
        loginDetails.setStatus("Active");
        loginDetails.setMustChangePassword(true);
        loginDetails.setFailedAttempts(0);
        sendPasswordSMS(customerId,password);
        return loginDetails;
    }

    private void sendPasswordSMS(Long customerId, String password) {
        LOGGER.info("Entering sendPasswordSMS for customerId {}",customerId);
        Optional<ContactInformation> contactInformation = contactInformationRepository.findByCustomerId(customerId);
        Optional<String> customer = contactInformationRepository.findCustomerByCustomerId(customerId);
        String customerMobileNumber=null;

        if(contactInformation.isPresent()){
            customerMobileNumber = contactInformation.get().getCountryCode()+ contactInformation.get().getPhoneNumber();
        }

      /*  if(Objects.nonNull(customerMobileNumber)){
            smsService.sendSMS(customerMobileNumber, CustomerRegistrationConstants.fromMobileNumber,"Dear "+customer.get()+ ", thanks for banking with Apna Bank. Your customer id is: "+
                    customerId + " and first time login password is:  "+password+" \n Please change this password to secure one after first time time.\n" +
                    "Thank you\n Apna Bank" );
        }

       */
        LOGGER.info("Successfully sent SMS to the customer with customerId {}",customer);
    }


    private String generateRandomPasswordForNewCustomer() {
        GeneratePasswordUtility generatePasswordUtility = new GeneratePasswordUtility();
        return generatePasswordUtility.generatePassword(12);
    }

}
