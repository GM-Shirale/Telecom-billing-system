package com.Telecom.Billing.Telecom_Billing_System.dto.response;

public record CustomerAddressResponse(
        Long addressId,
        Long customerId,
        String addressType,
        String addressLine,
        String city,
        String state,
        String postalCode,
        String country
) {
}
