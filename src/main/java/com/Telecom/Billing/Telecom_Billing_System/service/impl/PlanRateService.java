package com.Telecom.Billing.Telecom_Billing_System.service;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.PlanRateRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.PlanRateResponse;

import java.util.List;

public interface PlanRateService {

    PlanRateResponse createRate(
            Long planId,
            PlanRateRequest request
    );

    List<PlanRateResponse> getRatesByPlanId(
            Long planId
    );

    PlanRateResponse getRate(
            Long planId,
            Long rateId
    );

    PlanRateResponse updateRate(
            Long planId,
            Long rateId,
            PlanRateRequest request
    );

    void deleteRate(
            Long planId,
            Long rateId
    );
}