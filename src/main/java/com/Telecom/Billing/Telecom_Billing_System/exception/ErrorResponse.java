package com.Telecom.Billing.Telecom_Billing_System.exception;

import java.time.LocalDateTime;

public record ErrorResponse(int status, String message, LocalDateTime timestamp) {

}
