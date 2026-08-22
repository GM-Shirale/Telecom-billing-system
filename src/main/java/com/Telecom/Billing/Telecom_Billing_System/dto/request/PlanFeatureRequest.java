package com.Telecom.Billing.Telecom_Billing_System.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PlanFeatureRequest(
        @NotBlank(message = "Feature name is required")
        String featureName,

        String featureValue
) {
}
