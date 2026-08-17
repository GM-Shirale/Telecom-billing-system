package com.Telecom.Billing.Telecom_Billing_System.repository;

import com.Telecom.Billing.Telecom_Billing_System.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress,Long> {

    List<CustomerAddress> findByCustomerCustomerId(Long customerID);
}
