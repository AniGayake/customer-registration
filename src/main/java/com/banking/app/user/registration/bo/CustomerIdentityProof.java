package com.banking.app.user.registration.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigInteger;
import java.util.Objects;

@Entity
@JsonInclude(content = JsonInclude.Include.NON_NULL)
@Table(name = "identity_proof_info")
public class CustomerIdentityProof {
    @Id
    @JsonIgnore
    private Integer identityProofId;
    private Integer customerId;
    private BigInteger aadharNumber;
    private String panNumber;
    private String voterIdNumber;
    private String passportNumber;

    public Integer getIdentityProofId() {
        return identityProofId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigInteger getAadharNumber() {
        return aadharNumber;
    }

    public void setAadharNumber(BigInteger aadharNumber) {
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

    @Override
    public String toString() {
        return "CustomerIdentityProof{" +
                "identityProofId=" + identityProofId +
                ", customerId=" + customerId +
                ", aadharNumber=" + aadharNumber +
                ", panNumber='" + panNumber + '\'' +
                ", voterIdNumber='" + voterIdNumber + '\'' +
                ", passportNumber='" + passportNumber + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerIdentityProof that = (CustomerIdentityProof) o;
        return Objects.equals(identityProofId, that.identityProofId) && Objects.equals(customerId, that.customerId) && Objects.equals(aadharNumber, that.aadharNumber) && Objects.equals(panNumber, that.panNumber) && Objects.equals(voterIdNumber, that.voterIdNumber) && Objects.equals(passportNumber, that.passportNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identityProofId, customerId, aadharNumber, panNumber, voterIdNumber, passportNumber);
    }
}
