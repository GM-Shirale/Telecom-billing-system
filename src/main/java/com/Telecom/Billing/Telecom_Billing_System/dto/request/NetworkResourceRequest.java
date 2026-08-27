package com.Telecom.Billing.Telecom_Billing_System.dto.request;

import jakarta.validation.constraints.NotBlank;

public record NetworkResourceRequest(

        @NotBlank(message = "Resource code is required")
        String resourceCode,

        @NotBlank(message = "Resource type is required")
        String resourceType,

        @NotBlank(message = "Status is required")
        String status,

        String networkProvider

) {
}