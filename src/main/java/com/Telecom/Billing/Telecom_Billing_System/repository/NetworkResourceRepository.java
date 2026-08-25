package com.Telecom.Billing.Telecom_Billing_System.repository;

import com.Telecom.Billing.Telecom_Billing_System.entity.NetworkResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NetworkResourceRepository extends JpaRepository<NetworkResource, Long> {

    List<NetworkResource> findByResourceType(String resourceType);

    List<NetworkResource> findByStatus(String status);

    boolean existsByResourceCode(String resourceCode);
}