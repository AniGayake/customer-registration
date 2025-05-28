package com.banking.app.user.registration.dto.request;

import com.banking.app.user.registration.bo.ContactInformation;

public class CustomerOnboardingKafkaObject {
    private String type;
    private Long customerId;
    private String password;
    private String customerFirstname;
    private ContactInformation contactInformation;

    public CustomerOnboardingKafkaObject(String type,Long customerId, String password, String customerFirstname, ContactInformation contactInformation) {
        this.type=type;
        this.customerId = customerId;
        this.password = password;
        this.customerFirstname = customerFirstname;
        this.contactInformation = contactInformation;
    }

    public String getType() {
        return type;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ContactInformation getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(ContactInformation contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getCustomerFirstname() {
        return customerFirstname;
    }

    public void setCustomerFirstname(String customerFirstname) {
        this.customerFirstname = customerFirstname;
    }
}
