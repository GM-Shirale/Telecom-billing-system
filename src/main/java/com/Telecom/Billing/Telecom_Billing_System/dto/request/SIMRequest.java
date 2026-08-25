package com.Telecom.Billing.Telecom_Billing_System.dto.request;

import jakarta.validation.constraints.NotBlank;

public record SIMRequest(

        @NotBlank(message = "ICCID is required")
        String iccid,

        @NotBlank(message = "MSISDN is required")
        String msisdn,

        @NotBlank(message = "Status is required")
        String status,

        Long networkResourceId

) {
}