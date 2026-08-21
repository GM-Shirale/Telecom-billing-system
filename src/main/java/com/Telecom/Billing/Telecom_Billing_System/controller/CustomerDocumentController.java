package com.Telecom.Billing.Telecom_Billing_System.controller;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.CustomerDocumentRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.CustomerDocumentResponse;
import com.Telecom.Billing.Telecom_Billing_System.service.CustomerDocumentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/documents")
@RequiredArgsConstructor
public class CustomerDocumentController {

    private final CustomerDocumentService customerDocumentService;

    @PostMapping(consumes = "multipart/form-data")
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDocumentResponse uploadDocument(
            @PathVariable Long customerId,

            @RequestPart("request")
            CustomerDocumentRequest request,

            @RequestPart("file")
            MultipartFile file) {

        return customerDocumentService.uploadDocument(
                customerId,
                request,
                file
        );
    }

    @GetMapping
    public List<CustomerDocumentResponse> getDocuments(@PathVariable Long customerId) {

        return customerDocumentService
                .getDocumentsByCustomerId(customerId);
    }

    @GetMapping("/{documentId}")
    public CustomerDocumentResponse getDocument(
            @PathVariable Long customerId,
            @PathVariable Long documentId) {

        return customerDocumentService.getDocument(
                customerId,
                documentId
        );
    }
}