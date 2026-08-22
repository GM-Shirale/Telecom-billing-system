package com.Telecom.Billing.Telecom_Billing_System.repository;

import com.Telecom.Billing.Telecom_Billing_System.entity.PlanRate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlanRateRepository extends JpaRepository<PlanRate,Long> {


    List<PlanRate> findByPlanPlanId(Long planId);
}
