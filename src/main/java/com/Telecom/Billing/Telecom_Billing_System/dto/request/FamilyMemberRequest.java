package com.Telecom.Billing.Telecom_Billing_System.dto.request;

import jakarta.validation.constraints.NotBlank;

public record FamilyMemberRequest(

        @NotBlank(message = "Member name is required")
        String memberName,

        @NotBlank(message = "Member phone is required")
        String memberPhone,

        @NotBlank(message = "Relationship is required")
        String relationship

) {
}