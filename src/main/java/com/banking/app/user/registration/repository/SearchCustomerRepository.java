package com.banking.app.user.registration.repository;

import com.banking.app.user.registration.bo.CustomerIdentityProof;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface SearchCustomerRepository extends JpaRepository<CustomerIdentityProof,Integer> {
    Optional<CustomerIdentityProof> findCustomerByAadharNumber(BigInteger aadharNumber);
    Optional<CustomerIdentityProof> findCustomerByPanNumber(String panNumber);
    Optional<CustomerIdentityProof> findCustomerByVoterIdNumber(String voterId);
    Optional<CustomerIdentityProof> findCustomerByPassportNumber(String passportNumber);

}
