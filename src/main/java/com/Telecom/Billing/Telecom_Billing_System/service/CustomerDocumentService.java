package com.Telecom.Billing.Telecom_Billing_System.service;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.CustomerDocumentRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.CustomerDocumentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CustomerDocumentService {

    CustomerDocumentResponse uploadDocument(
            Long customerId,
            CustomerDocumentRequest request,
            MultipartFile file
    );

    List<CustomerDocumentResponse> getDocumentsByCustomerId(
            Long customerId
    );

    CustomerDocumentResponse getDocument(
            Long customerId,
            Long documentId
    );
}
