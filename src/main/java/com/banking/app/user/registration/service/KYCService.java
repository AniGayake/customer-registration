package com.banking.app.user.registration.service;

import com.banking.app.user.registration.bo.CustomerIdentityProof;
import com.banking.app.user.registration.bo.KYCDetails;
import com.banking.app.user.registration.dto.request.KYCDetailsRequest;
import com.banking.app.user.registration.exception.customExceptions.KYCRegistrationException;
import com.banking.app.user.registration.repository.KYCRepository;
import com.banking.app.user.registration.repository.IdentityProofInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
public class KYCService {
    private static final Logger LOGGER = LoggerFactory.getLogger(KYCService.class);
    private final KYCRepository kycRepository;
    private final IdentityProofInfoRepository identityProofInfoRepository;

    @Autowired
    public KYCService(final KYCRepository kycRepository,final IdentityProofInfoRepository identityProofInfoRepository) {
        this.kycRepository = kycRepository;
        this.identityProofInfoRepository = identityProofInfoRepository;
    }

    @Transactional
    public KYCDetails customerKYC(final KYCDetailsRequest kycDetailsRequest){
        LOGGER.info("Entering into customerKYC service");
        KYCDetails kycDetails= pupulateKYCDetails(kycDetailsRequest);
        KYCDetails registeredKYC = kycRepository.save(kycDetails);
        registerIdentityProof(kycDetailsRequest);
        if (Objects.nonNull(registeredKYC)){
            LOGGER.info("Exiting customerKYC Service");
            return registeredKYC;
        }else {
            throw new KYCRegistrationException("Error while KYC Registration");
        }
    }

    private void registerIdentityProof(KYCDetailsRequest kycDetailsRequest) {
        CustomerIdentityProof customerIdentityProof = new CustomerIdentityProof();
        customerIdentityProof.setCustomerId(kycDetailsRequest.getCustomerId());
        customerIdentityProof.setAadharNumber(kycDetailsRequest.getAadharNumber());
        customerIdentityProof.setPanNumber(kycDetailsRequest.getPanNumber());
        LOGGER.info("Registering the identity proof information along with KYC");
        identityProofInfoRepository.save(customerIdentityProof);
        LOGGER.info("Registered Customer Identity Proofs");
    }

    private KYCDetails pupulateKYCDetails(KYCDetailsRequest kycDetailsRequest) {
        KYCDetails kycDetails = new KYCDetails();
        kycDetails.setCustomerId(kycDetailsRequest.getCustomerId());
        kycDetails.setExpiryDate(kycDetailsRequest.getExpiryDate());
        kycDetails.setIssueDate(kycDetailsRequest.getIssueDate());
        kycDetails.setIdType("AADHAR");
        kycDetails.setIdNumber(kycDetailsRequest.getAadharNumber());
        return kycDetails;
    }
}
