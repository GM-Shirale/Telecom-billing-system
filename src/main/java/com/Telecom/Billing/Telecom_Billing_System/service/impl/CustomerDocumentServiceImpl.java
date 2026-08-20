package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.CustomerDocumentRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.CustomerDocumentResponse;
import com.Telecom.Billing.Telecom_Billing_System.entity.Customer;
import com.Telecom.Billing.Telecom_Billing_System.entity.CustomerDocument;
import com.Telecom.Billing.Telecom_Billing_System.exception.ResourceNotFoundException;
import com.Telecom.Billing.Telecom_Billing_System.repository.CustomerDocumentRepository;
import com.Telecom.Billing.Telecom_Billing_System.repository.CustomerRepository;
import com.Telecom.Billing.Telecom_Billing_System.service.CustomerDocumentService;
import com.Telecom.Billing.Telecom_Billing_System.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import com.Telecom.Billing.Telecom_Billing_System.entity.VerificationStatus;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerDocumentServiceImpl implements CustomerDocumentService {

    private final CustomerRepository customerRepository;
    private final CustomerDocumentRepository customerDocumentRepository;
    private final FileStorageService fileStorageService;


    @Override
    @Transactional
    public CustomerDocumentResponse uploadDocument(
            Long customerId,
            CustomerDocumentRequest request,
            MultipartFile file) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + customerId
                        ));

        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException(
                    "File cannot be empty"
            );
        }

        try {

            String filePath =
                    fileStorageService.storeFile(
                            file,
                            customerId
                    );

            String originalFileName =
                    file.getOriginalFilename();

            String storedFileName =
                    Paths.get(filePath)
                            .getFileName()
                            .toString();

            CustomerDocument document =
                    CustomerDocument.builder()
                            .customer(customer)
                            .documentType(request.documentType())
                            .originalFileName(originalFileName)
                            .storedFileName(storedFileName)
                            .filePath(filePath)
                            .contentType(file.getContentType())
                            .fileSize(file.getSize())
                            .verificationStatus(
                                    VerificationStatus.PENDING
                            )
                            .build();

            CustomerDocument savedDocument =
                    customerDocumentRepository.save(document);

            return mapToResponse(savedDocument);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Failed to store document",
                    e
            );
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDocumentResponse> getDocumentsByCustomerId(
            Long customerId) {

        customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + customerId
                        ));

        return customerDocumentRepository
                .findByCustomerCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDocumentResponse getDocument(
            Long customerId,
            Long documentId) {

        customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + customerId
                        ));

        CustomerDocument document =
                customerDocumentRepository.findById(documentId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Document not found with id: "
                                                + documentId
                                ));

        if (!document.getCustomer()
                .getCustomerId()
                .equals(customerId)) {

            throw new ResourceNotFoundException(
                    "Document " + documentId +
                            " does not belong to customer " + customerId
            );
        }

        return mapToResponse(document);
    }

    private CustomerDocumentResponse mapToResponse(
            CustomerDocument document) {

        return new CustomerDocumentResponse(
                document.getDocumentId(),
                document.getCustomer().getCustomerId(),
                document.getDocumentType(),
                document.getOriginalFileName(),
                document.getContentType(),
                document.getFileSize(),
                document.getVerificationStatus(),
                document.getVerificationRemarks(),
                document.getUploadedAt(),
                document.getVerifiedAt()
        );
    }
}