package com.Telecom.Billing.Telecom_Billing_System.dto.response;

public record PlanFeatureResponse(

        Long featureId,

        Long planId,

        String featureName,

        String featureValue

) {
}
