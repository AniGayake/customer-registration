package com.banking.app.user.registration.repository;

import com.banking.app.user.registration.bo.ContactInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactInformationRepository extends JpaRepository<ContactInformation,Long> {
}
