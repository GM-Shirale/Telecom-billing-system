package com.Telecom.Billing.Telecom_Billing_System.repository;

import com.Telecom.Billing.Telecom_Billing_System.entity.SIM;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface SIMRepository
        extends JpaRepository<SIM, Long> {

    boolean existsByIccid(String iccid);

    boolean existsByMsisdn(String msisdn);

    List<SIM> findByStatus(String status);

}