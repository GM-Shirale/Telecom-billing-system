package com.Telecom.Billing.Telecom_Billing_System.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record SubscriptionRequest(
        @NotNull(message = "Customer ID is required")
        Long customerId,

        @NotNull(message = "Plan ID is required")
        Long planId,

        @NotNull(message = "Start date is required")
        LocalDate startDate
) {
}
