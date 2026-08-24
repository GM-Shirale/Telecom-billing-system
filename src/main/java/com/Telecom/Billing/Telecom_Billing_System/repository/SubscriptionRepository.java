package com.Telecom.Billing.Telecom_Billing_System.repository;

import com.Telecom.Billing.Telecom_Billing_System.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscriptionRepository
        extends JpaRepository<Subscription, Long> {

    List<Subscription> findByCustomerCustomerId(Long customerId);

    List<Subscription> findByPlanPlanId(Long planId);

    List<Subscription> findByStatus(String status);
}