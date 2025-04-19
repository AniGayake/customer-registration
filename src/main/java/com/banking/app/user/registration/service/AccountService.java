package com.banking.app.user.registration.service;

import com.banking.app.user.registration.bo.Account;
import com.banking.app.user.registration.dto.request.AccountRequest;
import org.springframework.stereotype.Service;

@Service
public interface AccountService {
    Account createAccount(AccountRequest accountRequest);
}
