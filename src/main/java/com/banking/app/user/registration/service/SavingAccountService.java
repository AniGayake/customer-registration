package com.banking.app.user.registration.service;

import com.banking.app.user.registration.bo.SavingAccount;
import com.banking.app.user.registration.constants.CustomerRegistrationConstants;
import com.banking.app.user.registration.dto.request.AccountRequest;
import com.banking.app.user.registration.repository.SavingAccountRepository;
import com.banking.app.user.registration.utils.GenerateAccountNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class SavingAccountService implements AccountService{
    private GenerateAccountNumber generateAccountNumber;
    private SavingAccountRepository savingAccountRepository;

    @Autowired
    public SavingAccountService(SavingAccountRepository savingAccountRepository,final GenerateAccountNumber generateAccountNumber) {
        this.savingAccountRepository = savingAccountRepository;
        this.generateAccountNumber = generateAccountNumber;
    }

    @Override
    public SavingAccount createAccount(AccountRequest accountRequest) {
        SavingAccount savingAccount = new SavingAccount();
        savingAccount.setAccountNumber(generateAccountNumber());
        savingAccount.setBranchCode(accountRequest.getBranchCode());
        savingAccount.setCustomerId(accountRequest.getCustomerId());
        savingAccount.setIfscCode(accountRequest.getIfscCode());
        savingAccount.setAccountTypeCode(accountRequest.getAccountType());
        savingAccount.setCreatedAt(LocalDateTime.now());
        savingAccount.setInterestRate(7.00);
        savingAccount.setMinimumBalance(new BigDecimal(0.0));
        savingAccount.setCurrency("INR");
        savingAccount.setBalance(new BigDecimal(0.0));
        savingAccount.setStatusCode("A");
        savingAccount.setWithdrawalLimit(CustomerRegistrationConstants.SAVING_ACCT_WITHDRAWL_LIMIT);
        return savingAccountRepository.save(savingAccount);
    }

    private String generateAccountNumber() {
        return generateAccountNumber.generateUniqueAccountNumber();
    }
}
