package com.Telecom.Billing.Telecom_Billing_System.dto.response;

import java.time.LocalDateTime;

public record SubscriptionHistoryResponse(

        Long historyId,

        Long subscriptionId,

        String action,

        String description,

        LocalDateTime changedAt

) {
}