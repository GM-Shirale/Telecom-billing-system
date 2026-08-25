package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.SIMRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.SIMResponse;
import com.Telecom.Billing.Telecom_Billing_System.entity.NetworkResource;
import com.Telecom.Billing.Telecom_Billing_System.entity.SIM;
import com.Telecom.Billing.Telecom_Billing_System.exception.ResourceNotFoundException;
import com.Telecom.Billing.Telecom_Billing_System.repository.NetworkResourceRepository;
import com.Telecom.Billing.Telecom_Billing_System.repository.SIMRepository;
import com.Telecom.Billing.Telecom_Billing_System.service.SIMService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SIMServiceImpl implements SIMService {

    private final SIMRepository simRepository;
    private final NetworkResourceRepository networkResourceRepository;

    @Override
    @Transactional
    public SIMResponse createSIM(SIMRequest request) {

        if (simRepository.existsByIccid(request.iccid())) {
            throw new IllegalArgumentException(
                    "SIM with ICCID already exists: "
                            + request.iccid()
            );
        }

        if (simRepository.existsByMsisdn(request.msisdn())) {
            throw new IllegalArgumentException(
                    "SIM with MSISDN already exists: "
                            + request.msisdn()
            );
        }

        NetworkResource networkResource =
                networkResourceRepository.findById(
                        request.networkResourceId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Network resource not found with id: "
                                        + request.networkResourceId()
                        ));

        // SIM can only be connected to MOBILE resource
        if (!"MOBILE".equalsIgnoreCase(
                networkResource.getResourceType())) {

            throw new IllegalArgumentException(
                    "SIM can only be assigned to MOBILE network resources"
            );
        }

        // Resource must be available
        if (!"AVAILABLE".equalsIgnoreCase(
                networkResource.getStatus())) {

            throw new IllegalArgumentException(
                    "Network resource is not available"
            );
        }

        SIM sim = SIM.builder()
                .iccid(request.iccid())
                .msisdn(request.msisdn())
                .status(request.status())
                .networkResource(networkResource)
                .build();

        // Allocate network resource to this SIM
        networkResource.setStatus("ALLOCATED");


        SIM savedSIM = simRepository.save(sim);

        return mapToResponse(savedSIM);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SIMResponse> getAllSIMs() {

        return simRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public SIMResponse getSIM(Long simId) {

        SIM sim = simRepository.findById(simId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "SIM not found with id: " + simId
                        ));

        return mapToResponse(sim);
    }

    @Override
    @Transactional
    public SIMResponse updateSIM(
            Long simId,
            SIMRequest request) {

        SIM sim = simRepository.findById(simId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "SIM not found with id: " + simId
                        ));

        NetworkResource networkResource =
                networkResourceRepository.findById(
                        request.networkResourceId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Network resource not found with id: "
                                        + request.networkResourceId()
                        ));

        if (!"MOBILE".equalsIgnoreCase(
                networkResource.getResourceType())) {

            throw new IllegalArgumentException(
                    "SIM can only be assigned to MOBILE network resources"
            );
        }

        sim.setIccid(request.iccid());
        sim.setMsisdn(request.msisdn());
        sim.setStatus(request.status());
        sim.setNetworkResource(networkResource);

        return mapToResponse(
                simRepository.save(sim)
        );
    }

    @Override
    @Transactional
    public void deleteSIM(Long simId) {

        SIM sim = simRepository.findById(simId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "SIM not found with id: " + simId
                        ));

        simRepository.delete(sim);
    }

    private SIMResponse mapToResponse(SIM sim) {

        NetworkResource resource =
                sim.getNetworkResource();

        return new SIMResponse(
                sim.getSimId(),
                sim.getIccid(),
                sim.getMsisdn(),
                sim.getStatus(),
                resource != null
                        ? resource.getResourceId()
                        : null,
                resource != null
                        ? resource.getResourceCode()
                        : null,
                resource != null
                        ? resource.getResourceType()
                        : null,
                sim.getActivatedAt(),
                sim.getDeactivatedAt(),
                sim.getCreatedAt(),
                sim.getUpdatedAt()
        );
    }
}