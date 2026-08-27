package com.Telecom.Billing.Telecom_Billing_System.dto.response;

import com.Telecom.Billing.Telecom_Billing_System.enums.ServiceType;

import java.time.LocalDateTime;

public record PlanResponse(
        Long planId,

        String planCode,

        String planName,

        String description,

        Boolean active,

        LocalDateTime createdAt,

        LocalDateTime updatedAt,

        ServiceType serviceType
) {
}
