package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.entity.CustomerDocument;
import com.Telecom.Billing.Telecom_Billing_System.entity.VerificationStatus;
import com.Telecom.Billing.Telecom_Billing_System.service.DocumentVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DocumentVerificationServiceImpl
        implements DocumentVerificationService {

    @Override
    @Transactional
    public void verifyDocument(CustomerDocument document) {

        System.out.println(
                "Started verification for document: "
                        + document.getDocumentId()
        );

        // Temporary validation.
        // OCR and actual document validation will be added later.

        if (document.getFileSize() == null ||
                document.getFileSize() == 0) {

            document.setVerificationStatus(
                    VerificationStatus.REJECTED
            );

            document.setVerificationRemarks(
                    "Uploaded document is empty"
            );

            return;
        }

        document.setVerificationStatus(
                VerificationStatus.VERIFIED
        );

        document.setVerificationRemarks(
                "Basic document validation successful"
        );
    }
}