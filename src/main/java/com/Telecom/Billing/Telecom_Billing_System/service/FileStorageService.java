package com.Telecom.Billing.Telecom_Billing_System.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {

    String storeFile(MultipartFile file, Long customerId) throws IOException;
}