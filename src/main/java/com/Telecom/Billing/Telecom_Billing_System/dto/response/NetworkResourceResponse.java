package com.Telecom.Billing.Telecom_Billing_System.dto.response;

import java.time.LocalDateTime;

public record NetworkResourceResponse(

        Long resourceId,
        String resourceCode,
        String resourceType,
        String status,
        String networkProvider,
        LocalDateTime activatedAt,
        LocalDateTime deactivatedAt,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
