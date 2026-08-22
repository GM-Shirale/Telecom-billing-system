package com.Telecom.Billing.Telecom_Billing_System.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PlanRateRequest(

        @NotBlank(message = "Rate type is required")
        String rateType,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.0", inclusive = false,
                message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotBlank(message = "Unit is required")
        String unit

) {
}