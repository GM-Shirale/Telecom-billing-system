package com.Telecom.Billing.Telecom_Billing_System.service;

import com.Telecom.Billing.Telecom_Billing_System.entity.CustomerDocument;
import com.Telecom.Billing.Telecom_Billing_System.repository.CustomerDocumentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DocumentVerificationAsyncService {

    private final DocumentVerificationService documentVerificationService;
    private final CustomerDocumentRepository customerDocumentRepository;

    @Async
    public void processDocument(Long documentId) {

        CustomerDocument document =
                customerDocumentRepository.findById(documentId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Document not found: "
                                                + documentId
                                ));

        documentVerificationService.verifyDocument(document);

        customerDocumentRepository.save(document);

        System.out.println(
                "Completed verification for document: "
                        + documentId
        );
    }
}