package com.Telecom.Billing.Telecom_Billing_System.service;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.PlanRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.PlanResponse;

import java.util.List;

public interface PlanService {

    PlanResponse createPlan(PlanRequest request);

    List<PlanResponse> getAllPlans();

    PlanResponse getPlanById(Long planId);

    PlanResponse updatePlan(Long planId, PlanRequest request);

    void deletePlan(Long planId);
}