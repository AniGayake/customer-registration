package com.banking.app.user.registration.repository;

import com.banking.app.user.registration.bo.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
