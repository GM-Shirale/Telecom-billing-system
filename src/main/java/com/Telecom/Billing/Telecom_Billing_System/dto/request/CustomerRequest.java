package com.Telecom.Billing.Telecom_Billing_System.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record  CustomerRequest(

        @NotBlank
        String firstName,

        @NotBlank
        String lastName,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "^[0-9]{10}$")
        String phone
) {
}