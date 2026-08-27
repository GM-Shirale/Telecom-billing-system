package com.Telecom.Billing.Telecom_Billing_System.dto.request;

import com.Telecom.Billing.Telecom_Billing_System.enums.ServiceType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PlanRequest(
        @NotBlank(message = "Plan code is required")
        String planCode,

        @NotBlank(message = "Plan name is required")
        String planName,

        String description,

        Boolean active,

        @NotNull(message = "Service type is required")
        ServiceType serviceType

) {
}
