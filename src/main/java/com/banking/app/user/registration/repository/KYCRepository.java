package com.banking.app.user.registration.repository;

import com.banking.app.user.registration.bo.KYCDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KYCRepository extends JpaRepository<KYCDetails,Long> {

}
