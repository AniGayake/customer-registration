package com.banking.app.user.registration.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Transient;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchCustomerInSystemResponse {
    @Transient
    private Boolean doesExists;
    private Long customerId;
    private String firstName;
    private String lastName;
    private String aadharNumber;
    private String panNumber;
    private String voterIdNumber;
    private String passportNumber;


    public Boolean getDoesExists() {
        return doesExists;
    }

    public void setDoesExists(Boolean doesExists) {
        this.doesExists = doesExists;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(String aadharNumber) {
        this.aadharNumber = aadharNumber;
    }

    public String getPanNumber() {
        return panNumber;
    }

    public void setPanNumber(String panNumber) {
        this.panNumber = panNumber;
    }

    public String getVoterIdNumber() {
        return voterIdNumber;
    }

    public void setVoterIdNumber(String voterIdNumber) {
        this.voterIdNumber = voterIdNumber;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "SearchCustomerInSystemResponse{" +
                "doesExists=" + doesExists +
                ", customerId=" + customerId +
                ", aadharNumber='" + aadharNumber + '\'' +
                ", panNumber='" + panNumber + '\'' +
                ", voterIdNumber='" + voterIdNumber + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
