package com.Telecom.Billing.Telecom_Billing_System.controller;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.PlanRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.PlanResponse;
import com.Telecom.Billing.Telecom_Billing_System.service.PlanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/plans")
@RequiredArgsConstructor
public class PlanController {

    private final PlanService planService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PlanResponse createPlan( @Valid @RequestBody PlanRequest request) {

        return planService.createPlan(request);
    }

    @GetMapping
    public List<PlanResponse> getAllPlans() {

        return planService.getAllPlans();
    }

    @GetMapping("/{planId}")
    public PlanResponse getPlanById( @PathVariable Long planId) {

        return planService.getPlanById(planId);
    }

    @PutMapping("/{planId}")
    public PlanResponse updatePlan(@PathVariable Long planId, @Valid @RequestBody PlanRequest request) {

        return planService.updatePlan(planId, request);
    }

    @DeleteMapping("/{planId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlan( @PathVariable Long planId) {

        planService.deletePlan(planId);
    }
}