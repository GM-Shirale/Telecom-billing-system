package com.Telecom.Billing.Telecom_Billing_System.repository;


import com.Telecom.Billing.Telecom_Billing_System.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByCustomerCode(String customerCode);

    Optional<Customer> findByEmail(String email);

    boolean existsByPhone(String phone);
}
