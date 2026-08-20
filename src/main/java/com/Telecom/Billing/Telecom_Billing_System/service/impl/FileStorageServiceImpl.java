package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements FileStorageService {

    private final Path uploadDirectory;

    public FileStorageServiceImpl(
            @Value("${file.upload-dir}") String uploadDir) {

        this.uploadDirectory = Paths.get(uploadDir)
                .toAbsolutePath()
                .normalize();

        try {
            Files.createDirectories(this.uploadDirectory);
        } catch (IOException e) {
            throw new RuntimeException(
                    "Could not create upload directory",
                    e
            );
        }
    }

    @Override
    public String storeFile(
            MultipartFile file,
            Long customerId) throws IOException {

        String originalFileName =
                StringUtils.cleanPath(
                        file.getOriginalFilename()
                );

        String extension = "";

        int dotIndex = originalFileName.lastIndexOf(".");

        if (dotIndex >= 0) {
            extension =
                    originalFileName.substring(dotIndex);
        }

        String storedFileName =
                customerId + "_" +
                        UUID.randomUUID() +
                        extension;

        Path customerDirectory =
                uploadDirectory.resolve(
                        String.valueOf(customerId)
                );

        Files.createDirectories(customerDirectory);

        Path targetLocation =
                customerDirectory.resolve(storedFileName);

        Files.copy(
                file.getInputStream(),
                targetLocation,
                StandardCopyOption.REPLACE_EXISTING
        );

        return targetLocation.toString();
    }
}