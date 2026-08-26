package com.Telecom.Billing.Telecom_Billing_System.repository;

import com.Telecom.Billing.Telecom_Billing_System.entity.NumberPortability;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NumberPortabilityRepository
        extends JpaRepository<NumberPortability, Long> {

    List<NumberPortability> findByMsisdn(String msisdn);

    boolean existsByMsisdnAndStatus(
            String msisdn,
            String status
    );
}