package com.banking.app.user.registration.repository;

import com.banking.app.user.registration.bo.LoginDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginRepository extends JpaRepository<LoginDetails,Long> {
}
