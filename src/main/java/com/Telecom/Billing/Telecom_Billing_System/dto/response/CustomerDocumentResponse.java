package com.Telecom.Billing.Telecom_Billing_System.dto.response;

import com.Telecom.Billing.Telecom_Billing_System.entity.DocumentType;
import com.Telecom.Billing.Telecom_Billing_System.entity.VerificationStatus;

import java.time.LocalDateTime;

public record CustomerDocumentResponse(
        Long documentId,

        Long customerId,

        DocumentType documentType,

        String originalFileName,

        String contentType,

        Long fileSize,

        VerificationStatus verificationStatus,

        String verificationRemarks,

        LocalDateTime uploadedAt,

        LocalDateTime verifiedAt
) {


}
