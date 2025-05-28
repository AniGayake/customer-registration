package com.banking.app.user.registration.service;

//import com.banking.app.sms.utility.service.SMSService;
import com.banking.app.user.registration.bo.ContactInformation;
import com.banking.app.user.registration.bo.LoginDetails;
import com.banking.app.user.registration.constants.CustomerRegistrationConstants;
import com.banking.app.user.registration.dto.request.CustomerOnboardingKafkaObject;
import com.banking.app.user.registration.repository.ContactInformationRepository;
import com.banking.app.user.registration.repository.LoginRepository;
import com.banking.app.user.registration.utils.GeneratePasswordUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoginService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
    @Value("${kafka.customer.onboarding.topic}")
    private String CUSTOMER_ONBOARDING_TOPIC;
    private final LoginRepository loginRepository;
    private final ContactInformationRepository contactInformationRepository;
//    private final SMSService smsService;
    private KafkaTemplate<String,CustomerOnboardingKafkaObject> kafkaTemplate;

    public LoginService(final LoginRepository loginRepository,final ContactInformationRepository contactInformationRepository, KafkaTemplate<String,CustomerOnboardingKafkaObject> kafkaTemplate
    ) {
        this.loginRepository = loginRepository;
        this.contactInformationRepository = contactInformationRepository;
        this.kafkaTemplate=kafkaTemplate;

    }

    public void createLoginDetailsForNewCustomer(final Long customerId){
        LOGGER.info("Creating login details for customer with Id {}", customerId);
        LoginDetails loginDetails = populateLoginDetails(customerId);
        loginRepository.save(loginDetails);
        sendPasswordSMS(customerId,loginDetails.getPassword());
    }

    private LoginDetails populateLoginDetails(Long customerId) {
        LoginDetails loginDetails = new LoginDetails();
        String password = generateRandomPasswordForNewCustomer();
        String hashedPassword = new BCryptPasswordEncoder().encode(password);
        loginDetails.setCustomerId(customerId);
        loginDetails.setUsername("userani");
        loginDetails.setPassword(hashedPassword);
        System.out.println(password);
        loginDetails.setLastLogin(LocalDateTime.now());
        loginDetails.setLastFailedLogin(null);
        loginDetails.setStatus("Active");
        loginDetails.setMustChangePassword(true);
        loginDetails.setFailedAttempts(0);
        return loginDetails;
    }

    private void sendPasswordSMS(Long customerId, String password) {
        LOGGER.info("Entering sendPasswordSMS for customerId {}",customerId);
        Optional<ContactInformation> contactInformation = contactInformationRepository.findByCustomerId(customerId);
        Optional<String> customerFirstName = contactInformationRepository.findCustomerFirstnameByCustomerId(customerId);
        CustomerOnboardingKafkaObject onboardingKafkaObject = new CustomerOnboardingKafkaObject("CUSTOMER_ONBOARDING",customerId,password,customerFirstName.get(),contactInformation.get());
        kafkaTemplate.send(CUSTOMER_ONBOARDING_TOPIC,onboardingKafkaObject);
        LOGGER.info("Successfully sent SMS to the customer with customerId {}",customerId);
    }


    private String generateRandomPasswordForNewCustomer() {
        GeneratePasswordUtility generatePasswordUtility = new GeneratePasswordUtility();
        return generatePasswordUtility.generatePassword(12);
    }

}
