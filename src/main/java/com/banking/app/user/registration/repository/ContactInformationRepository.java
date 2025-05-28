package com.banking.app.user.registration.repository;

import com.banking.app.user.registration.bo.ContactInformation;
import com.banking.app.user.registration.bo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactInformationRepository extends JpaRepository<ContactInformation,Long> {
    @Query(value = "Select email from contact_info where customer_id = ?", nativeQuery = true)
    String getCustomerEmailId(Long customerId);

    @Query(value = "Select phone_number from contact_info where customer_id = ?", nativeQuery = true)
    String getCustomerMobileNumber(Long customerId);

    Optional<ContactInformation> findByCustomerId(Long customerId);

    @Query(value = "Select first_name from personal_info where customer_id = ?",nativeQuery = true)
    Optional<String> findCustomerFirstnameByCustomerId(Long customerId);
}
