package com.Telecom.Billing.Telecom_Billing_System.controller;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.NumberPortabilityRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.NumberPortabilityResponse;
import com.Telecom.Billing.Telecom_Billing_System.service.NumberPortabilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/number-portability")
@RequiredArgsConstructor
public class NumberPortabilityController {

    private final NumberPortabilityService numberPortabilityService;

    // Create port request
    @PostMapping
    public ResponseEntity<NumberPortabilityResponse> requestPort(
            @Valid @RequestBody NumberPortabilityRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(numberPortabilityService.requestPort(request));
    }

    // Get all port requests
    @GetMapping
    public ResponseEntity<List<NumberPortabilityResponse>>
    getAllPortRequests() {

        return ResponseEntity.ok(
                numberPortabilityService.getAllPortRequests()
        );
    }

    // Get port request by ID
    @GetMapping("/{portId}")
    public ResponseEntity<NumberPortabilityResponse>
    getPortRequest(@PathVariable Long portId) {

        return ResponseEntity.ok(
                numberPortabilityService
                        .getPortRequest(portId)
        );
    }

    // Approve port request
    @PutMapping("/{portId}/approve")
    public ResponseEntity<NumberPortabilityResponse>
    approvePort(@PathVariable Long portId) {

        return ResponseEntity.ok(
                numberPortabilityService
                        .approvePort(portId)
        );
    }

    // Complete port request
    @PutMapping("/{portId}/complete")
    public ResponseEntity<NumberPortabilityResponse>
    completePort(@PathVariable Long portId) {

        return ResponseEntity.ok(
                numberPortabilityService
                        .completePort(portId)
        );
    }

    // Reject port request
    @PutMapping("/{portId}/reject")
    public ResponseEntity<NumberPortabilityResponse>
    rejectPort(
            @PathVariable Long portId,
            @RequestParam String reason) {

        return ResponseEntity.ok(
                numberPortabilityService
                        .rejectPort(portId, reason)
        );
    }
}