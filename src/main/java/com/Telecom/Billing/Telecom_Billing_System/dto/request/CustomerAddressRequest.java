package com.Telecom.Billing.Telecom_Billing_System.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerAddressRequest(
        @NotBlank(message = "Address type is required")
        String addressType,

        @NotBlank(message = "Address line is required")
        @Size(max = 200)
        String addressLine,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state,

        @NotBlank(message = "Postal code is required")
        String postalCode,

        @NotBlank(message = "Country is required")
        String country
) {
}
