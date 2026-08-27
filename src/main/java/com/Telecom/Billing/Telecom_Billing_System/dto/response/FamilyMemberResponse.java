package com.Telecom.Billing.Telecom_Billing_System.dto.response;

import java.time.LocalDateTime;

public record FamilyMemberResponse(

        Long familyMemberId,

        Long subscriptionId,

        String memberName,

        String memberPhone,

        String relationship,

        String status,

        LocalDateTime createdAt,

        LocalDateTime updatedAt

) {
}