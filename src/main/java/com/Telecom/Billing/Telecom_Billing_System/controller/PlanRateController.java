package com.Telecom.Billing.Telecom_Billing_System.controller;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.PlanRateRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.PlanRateResponse;
import com.Telecom.Billing.Telecom_Billing_System.service.PlanRateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans/{planId}/rates")
@RequiredArgsConstructor
public class PlanRateController {

    private final PlanRateService planRateService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanRateResponse createRate(
            @PathVariable Long planId,
            @Valid @RequestBody PlanRateRequest request) {

        return planRateService.createRate(
                planId,
                request
        );
    }

    @GetMapping
    public List<PlanRateResponse> getRates(
            @PathVariable Long planId) {

        return planRateService
                .getRatesByPlanId(planId);
    }

    @GetMapping("/{rateId}")
    public PlanRateResponse getRate(
            @PathVariable Long planId,
            @PathVariable Long rateId) {

        return planRateService.getRate(
                planId,
                rateId
        );
    }

    @PutMapping("/{rateId}")
    public PlanRateResponse updateRate(
            @PathVariable Long planId,
            @PathVariable Long rateId,
            @Valid @RequestBody PlanRateRequest request) {

        return planRateService.updateRate(
                planId,
                rateId,
                request
        );
    }

    @DeleteMapping("/{rateId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRate(
            @PathVariable Long planId,
            @PathVariable Long rateId) {

        planRateService.deleteRate(
                planId,
                rateId
        );
    }
}