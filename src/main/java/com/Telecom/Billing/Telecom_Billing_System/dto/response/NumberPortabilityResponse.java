package com.Telecom.Billing.Telecom_Billing_System.dto.response;

import java.time.LocalDateTime;

public record NumberPortabilityResponse(

        Long portId,
        String msisdn,
        String oldProvider,
        String newProvider,
        String status,
        LocalDateTime portRequestDate,
        LocalDateTime portDate,
        String reason,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}