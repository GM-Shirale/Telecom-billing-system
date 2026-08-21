package com.Telecom.Billing.Telecom_Billing_System.dto.request;

import jakarta.validation.constraints.NotBlank;

public record PlanRequest(
        @NotBlank(message = "Plan code is required")
        String planCode,

        @NotBlank(message = "Plan name is required")
        String planName,

        String description,

        Boolean active

) {
}
