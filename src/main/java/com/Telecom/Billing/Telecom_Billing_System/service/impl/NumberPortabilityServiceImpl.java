package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.NumberPortabilityRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.NumberPortabilityResponse;
import com.Telecom.Billing.Telecom_Billing_System.entity.NumberPortability;
import com.Telecom.Billing.Telecom_Billing_System.exception.ResourceNotFoundException;
import com.Telecom.Billing.Telecom_Billing_System.repository.NumberPortabilityRepository;
import com.Telecom.Billing.Telecom_Billing_System.service.NumberPortabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NumberPortabilityServiceImpl
        implements NumberPortabilityService {

    private final NumberPortabilityRepository
            numberPortabilityRepository;

    @Override
    @Transactional
    public NumberPortabilityResponse requestPort(
            NumberPortabilityRequest request) {

        if (request.oldProvider()
                .equalsIgnoreCase(request.newProvider())) {

            throw new IllegalArgumentException(
                    "Old provider and new provider cannot be same"
            );
        }

        if (numberPortabilityRepository
                .existsByMsisdnAndStatus(
                        request.msisdn(),
                        "REQUESTED")) {

            throw new IllegalArgumentException(
                    "Port request already exists for MSISDN: "
                            + request.msisdn()
            );
        }

        NumberPortability port =
                NumberPortability.builder()
                        .msisdn(request.msisdn())
                        .oldProvider(request.oldProvider())
                        .newProvider(request.newProvider())
                        .status("REQUESTED")
                        .portRequestDate(LocalDateTime.now())
                        .reason(request.reason())
                        .build();

        return mapToResponse(
                numberPortabilityRepository.save(port)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<NumberPortabilityResponse> getAllPortRequests() {

        return numberPortabilityRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public NumberPortabilityResponse getPortRequest(
            Long portId) {

        NumberPortability port =
                numberPortabilityRepository.findById(portId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Port request not found with id: "
                                                + portId
                                ));

        return mapToResponse(port);
    }

    @Override
    @Transactional
    public NumberPortabilityResponse approvePort(
            Long portId) {

        NumberPortability port =
                findPortRequest(portId);

        if (!"REQUESTED".equalsIgnoreCase(
                port.getStatus())) {

            throw new IllegalArgumentException(
                    "Only REQUESTED port requests can be approved"
            );
        }

        port.setStatus("APPROVED");

        return mapToResponse(
                numberPortabilityRepository.save(port)
        );
    }

    @Override
    @Transactional
    public NumberPortabilityResponse completePort(
            Long portId) {

        NumberPortability port =
                findPortRequest(portId);

        if (!"APPROVED".equalsIgnoreCase(
                port.getStatus())) {

            throw new IllegalArgumentException(
                    "Only APPROVED port requests can be completed"
            );
        }

        port.setStatus("COMPLETED");
        port.setPortDate(LocalDateTime.now());

        return mapToResponse(
                numberPortabilityRepository.save(port)
        );
    }

    @Override
    @Transactional
    public NumberPortabilityResponse rejectPort(
            Long portId,
            String reason) {

        NumberPortability port =
                findPortRequest(portId);

        if ("COMPLETED".equalsIgnoreCase(
                port.getStatus())) {

            throw new IllegalArgumentException(
                    "Completed port request cannot be rejected"
            );
        }

        port.setStatus("REJECTED");
        port.setReason(reason);

        return mapToResponse(
                numberPortabilityRepository.save(port)
        );
    }

    private NumberPortability findPortRequest(
            Long portId) {

        return numberPortabilityRepository.findById(portId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Port request not found with id: "
                                        + portId
                        ));
    }

    private NumberPortabilityResponse mapToResponse(
            NumberPortability port) {

        return new NumberPortabilityResponse(
                port.getPortId(),
                port.getMsisdn(),
                port.getOldProvider(),
                port.getNewProvider(),
                port.getStatus(),
                port.getPortRequestDate(),
                port.getPortDate(),
                port.getReason(),
                port.getCreatedAt(),
                port.getUpdatedAt()
        );
    }
}