package com.Telecom.Billing.Telecom_Billing_System.expection;

import java.time.LocalDateTime;

public record ErrorResponse (

    int status,
    String message,
    LocalDateTime timestamp

){

}
