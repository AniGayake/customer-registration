package com.banking.app.user.registration.utils;

import com.banking.app.user.registration.repository.SavingAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class GenerateAccountNumber {

    private final SavingAccountRepository savingAccountRepository;

    @Autowired
    public GenerateAccountNumber(SavingAccountRepository savingAccountRepository) {
        this.savingAccountRepository = savingAccountRepository;
    }

    public String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = String.valueOf(100000000000L + new Random().nextLong(900000000000L));
        } while (accountNumberExists(accountNumber)); // Check in DB
        return accountNumber;
    }

    private boolean accountNumberExists(String accountNumber) {
       return savingAccountRepository.findById(accountNumber).isPresent();
    }
}
