package com.Telecom.Billing.Telecom_Billing_System.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record SubscriptionResponse(

        Long subscriptionId,

        Long customerId,

        Long planId,

        LocalDate startDate,

        LocalDate endDate,

        String status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}