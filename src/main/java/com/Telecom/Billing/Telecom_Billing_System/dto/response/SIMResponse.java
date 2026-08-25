package com.Telecom.Billing.Telecom_Billing_System.dto.response;

import java.time.LocalDateTime;

public record SIMResponse(

        Long simId,

        String iccid,

        String msisdn,

        String status,

        Long networkResourceId,

        String resourceCode,

        String resourceType,

        LocalDateTime activatedAt,

        LocalDateTime deactivatedAt,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}