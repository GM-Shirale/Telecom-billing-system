package com.Telecom.Billing.Telecom_Billing_System.dto.response;

public record CustomerResponse(
        Long customerId,
        String customerCode,
        String firstName,
        String lastName,
        String email,
        String phone,
        String status
) {

}