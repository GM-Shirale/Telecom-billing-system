package com.Telecom.Billing.Telecom_Billing_System.service;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.PlanFeatureRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.PlanFeatureResponse;

import java.util.List;

public interface PlanFeatureService {

    PlanFeatureResponse createFeature(
            Long planId,
            PlanFeatureRequest request
    );

    List<PlanFeatureResponse> getFeaturesByPlanId(
            Long planId
    );

    PlanFeatureResponse getFeature(
            Long planId,
            Long featureId
    );

    PlanFeatureResponse updateFeature(
            Long planId,
            Long featureId,
            PlanFeatureRequest request
    );

    void deleteFeature(
            Long planId,
            Long featureId
    );
}