package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.PlanRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.PlanResponse;
import com.Telecom.Billing.Telecom_Billing_System.entity.Plan;
import com.Telecom.Billing.Telecom_Billing_System.exception.ResourceNotFoundException;
import com.Telecom.Billing.Telecom_Billing_System.repository.PlanRepository;
import com.Telecom.Billing.Telecom_Billing_System.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanServiceImpl implements PlanService {

    private final PlanRepository planRepository;

    @Override
    @Transactional
    public PlanResponse createPlan(PlanRequest request) {

        Plan plan = Plan.builder()
                .planCode(request.planCode())
                .planName(request.planName())
                .description(request.description())
                .active(
                        request.active() != null
                                ? request.active()
                                : true
                )
                .serviceType(request.serviceType())
                .build();

        Plan savedPlan = planRepository.save(plan);

        return mapToResponse(savedPlan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanResponse> getAllPlans() {

        return planRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PlanResponse getPlanById(Long planId) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plan not found with id: " + planId
                        ));

        return mapToResponse(plan);
    }

    private PlanResponse mapToResponse(Plan plan) {

        return new PlanResponse(
                plan.getPlanId(),
                plan.getPlanCode(),
                plan.getPlanName(),
                plan.getDescription(),
                plan.getActive(),
                plan.getCreatedAt(),
                plan.getUpdatedAt(),
                plan.getServiceType()
        );
    }

    @Override
    @Transactional
    public PlanResponse updatePlan(
            Long planId,
            PlanRequest request) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plan not found with id: " + planId
                        ));

        plan.setPlanCode(request.planCode());
        plan.setPlanName(request.planName());
        plan.setDescription(request.description());

        if (request.active() != null) {
            plan.setActive(request.active());
        }

        Plan updatedPlan = planRepository.save(plan);

        return mapToResponse(updatedPlan);
    }

    @Override
    @Transactional
    public void deletePlan(Long planId) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plan not found with id: " + planId
                        ));

        planRepository.delete(plan);
    }
}