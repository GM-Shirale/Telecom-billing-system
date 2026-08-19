package com.Telecom.Billing.Telecom_Billing_System.dto.request;

import com.Telecom.Billing.Telecom_Billing_System.entity.DocumentType;
import jakarta.validation.constraints.NotNull;

public record CustomerDocumentRequest(
        @NotNull(message = "Document type is required")
        DocumentType documentType
) {
}
