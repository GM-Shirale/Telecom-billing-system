package com.Telecom.Billing.Telecom_Billing_System.controller;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.NetworkResourceRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.NetworkResourceResponse;
import com.Telecom.Billing.Telecom_Billing_System.service.NetworkResourceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/network-resources")
@RequiredArgsConstructor
public class NetworkResourceController {

    private final NetworkResourceService networkResourceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NetworkResourceResponse createResource(
            @Valid @RequestBody NetworkResourceRequest request) {

        return networkResourceService.createResource(request);
    }

    @GetMapping
    public List<NetworkResourceResponse> getAllResources() {

        return networkResourceService.getAllResources();
    }

    @GetMapping("/{resourceId}")
    public NetworkResourceResponse getResource(
            @PathVariable Long resourceId) {

        return networkResourceService.getResource(resourceId);
    }

    @PutMapping("/{resourceId}")
    public NetworkResourceResponse updateResource(
            @PathVariable Long resourceId,
            @Valid @RequestBody NetworkResourceRequest request) {

        return networkResourceService.updateResource(
                resourceId,
                request
        );
    }

    @DeleteMapping("/{resourceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteResource(
            @PathVariable Long resourceId) {

        networkResourceService.deleteResource(resourceId);
    }
}