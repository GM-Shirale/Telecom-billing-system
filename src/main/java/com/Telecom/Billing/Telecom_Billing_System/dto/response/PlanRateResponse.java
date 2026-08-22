package com.Telecom.Billing.Telecom_Billing_System.dto.response;

import java.math.BigDecimal;

public record PlanRateResponse(

        Long rateId,

        Long planId,

        String rateType,

        BigDecimal amount,

        String unit
) {
}
