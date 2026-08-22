package com.Telecom.Billing.Telecom_Billing_System.controller;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.PlanFeatureRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.PlanFeatureResponse;
import com.Telecom.Billing.Telecom_Billing_System.service.PlanFeatureService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans/{planId}/features")
@RequiredArgsConstructor
public class PlanFeatureController {

    private final PlanFeatureService planFeatureService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanFeatureResponse createFeature(
            @PathVariable Long planId,
            @Valid @RequestBody PlanFeatureRequest request) {

        return planFeatureService.createFeature(
                planId,
                request
        );
    }

    @GetMapping
    public List<PlanFeatureResponse> getFeatures(
            @PathVariable Long planId) {

        return planFeatureService
                .getFeaturesByPlanId(planId);
    }

    @GetMapping("/{featureId}")
    public PlanFeatureResponse getFeature(
            @PathVariable Long planId,
            @PathVariable Long featureId) {

        return planFeatureService.getFeature(
                planId,
                featureId
        );
    }

    @PutMapping("/{featureId}")
    public PlanFeatureResponse updateFeature(
            @PathVariable Long planId,
            @PathVariable Long featureId,
            @Valid @RequestBody PlanFeatureRequest request) {

        return planFeatureService.updateFeature(
                planId,
                featureId,
                request
        );
    }

    @DeleteMapping("/{featureId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteFeature(
            @PathVariable Long planId,
            @PathVariable Long featureId) {

        planFeatureService.deleteFeature(
                planId,
                featureId
        );
    }
}