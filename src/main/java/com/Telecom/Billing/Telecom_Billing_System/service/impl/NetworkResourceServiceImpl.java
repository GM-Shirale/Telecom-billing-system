package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.NetworkResourceRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.NetworkResourceResponse;
import com.Telecom.Billing.Telecom_Billing_System.entity.NetworkResource;
import com.Telecom.Billing.Telecom_Billing_System.exception.ResourceNotFoundException;
import com.Telecom.Billing.Telecom_Billing_System.repository.NetworkResourceRepository;
import com.Telecom.Billing.Telecom_Billing_System.service.NetworkResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NetworkResourceServiceImpl implements NetworkResourceService {

    private final NetworkResourceRepository networkResourceRepository;

    @Override
    @Transactional
    public NetworkResourceResponse createResource(NetworkResourceRequest request) {

        if (networkResourceRepository
                .existsByResourceCode(request.resourceCode())) {

            throw new IllegalArgumentException(
                    "Resource code already exists: "
                            + request.resourceCode()
            );
        }

        NetworkResource resource = NetworkResource.builder()
                .resourceCode(request.resourceCode())
                .resourceType(request.resourceType())
                .status(request.status())
                .networkProvider(request.networkProvider())
                .build();

        NetworkResource saved =
                networkResourceRepository.save(resource);

        return mapToResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<NetworkResourceResponse> getAllResources() {

        return networkResourceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public NetworkResourceResponse getResource(
            Long resourceId) {

        NetworkResource resource =
                networkResourceRepository.findById(resourceId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Network resource not found with id: "
                                                + resourceId
                                ));

        return mapToResponse(resource);
    }

    @Override
    @Transactional
    public NetworkResourceResponse updateResource(
            Long resourceId,
            NetworkResourceRequest request) {

        NetworkResource resource =
                networkResourceRepository.findById(resourceId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Network resource not found with id: "
                                                + resourceId
                                ));

        resource.setResourceCode(request.resourceCode());
        resource.setResourceType(request.resourceType());
        resource.setStatus(request.status());
        resource.setNetworkProvider(request.networkProvider());

        return mapToResponse(
                networkResourceRepository.save(resource)
        );
    }

    @Override
    @Transactional
    public void deleteResource(Long resourceId) {

        NetworkResource resource =
                networkResourceRepository.findById(resourceId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Network resource not found with id: "
                                                + resourceId
                                ));

        networkResourceRepository.delete(resource);
    }

    private NetworkResourceResponse mapToResponse(
            NetworkResource resource) {

        return new NetworkResourceResponse(
                resource.getResourceId(),
                resource.getResourceCode(),
                resource.getResourceType(),
                resource.getStatus(),
                resource.getNetworkProvider(),
                resource.getActivatedAt(),
                resource.getDeactivatedAt(),
                resource.getCreatedAt(),
                resource.getUpdatedAt()
        );
    }
}