package com.banking.app.user.registration.utils;

import com.banking.app.user.registration.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GenerateCustomerId {

    private CustomerRepository customerRepository;
    @Autowired
    public GenerateCustomerId(final CustomerRepository customerRepository){
        this.customerRepository= customerRepository;
    }

    public Long generateCustomerId(){
        Long customerId;
        do{
            customerId= 100000L + (long) (Math.random() * 900000L);
        }while (customerRepository.findByCustomerId(customerId).size()>0);
        return customerId;
    }

}
