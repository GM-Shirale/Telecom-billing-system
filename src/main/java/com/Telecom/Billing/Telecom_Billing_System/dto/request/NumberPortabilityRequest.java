package com.Telecom.Billing.Telecom_Billing_System.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record NumberPortabilityRequest(

        @NotBlank(message = "MSISDN is required")
        @Pattern(
                regexp = "^[0-9]{10}$",
                message = "MSISDN must contain exactly 10 digits"
        )
        String msisdn,

        @NotBlank(message = "Old provider is required")
        String oldProvider,

        @NotBlank(message = "New provider is required")
        String newProvider,

        String reason
) {
}