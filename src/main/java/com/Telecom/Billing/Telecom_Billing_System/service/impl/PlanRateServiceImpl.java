package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.PlanRateRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.PlanRateResponse;
import com.Telecom.Billing.Telecom_Billing_System.entity.Plan;
import com.Telecom.Billing.Telecom_Billing_System.entity.PlanRate;
import com.Telecom.Billing.Telecom_Billing_System.exception.ResourceNotFoundException;
import com.Telecom.Billing.Telecom_Billing_System.repository.PlanRateRepository;
import com.Telecom.Billing.Telecom_Billing_System.repository.PlanRepository;
import com.Telecom.Billing.Telecom_Billing_System.service.PlanRateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanRateServiceImpl implements PlanRateService {

    private final PlanRepository planRepository;
    private final PlanRateRepository planRateRepository;

    @Override
    @Transactional
    public PlanRateResponse createRate(
            Long planId,
            PlanRateRequest request) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plan not found with id: " + planId
                        ));

        PlanRate rate = PlanRate.builder()
                .plan(plan)
                .rateType(request.rateType())
                .amount(request.amount())
                .unit(request.unit())
                .build();

        PlanRate savedRate =
                planRateRepository.save(rate);

        return mapToResponse(savedRate);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanRateResponse> getRatesByPlanId(
            Long planId) {

        planRepository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plan not found with id: " + planId
                        ));

        return planRateRepository
                .findByPlanPlanId(planId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PlanRateResponse getRate(
            Long planId,
            Long rateId) {

        PlanRate rate =
                planRateRepository.findById(rateId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Rate not found with id: "
                                                + rateId
                                ));

        validateRateBelongsToPlan(rate, planId);

        return mapToResponse(rate);
    }

    @Override
    @Transactional
    public PlanRateResponse updateRate(
            Long planId,
            Long rateId,
            PlanRateRequest request) {

        PlanRate rate =
                planRateRepository.findById(rateId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Rate not found with id: "
                                                + rateId
                                ));

        validateRateBelongsToPlan(rate, planId);

        rate.setRateType(request.rateType());
        rate.setAmount(request.amount());
        rate.setUnit(request.unit());

        PlanRate updatedRate =
                planRateRepository.save(rate);

        return mapToResponse(updatedRate);
    }

    @Override
    @Transactional
    public void deleteRate(
            Long planId,
            Long rateId) {

        PlanRate rate =
                planRateRepository.findById(rateId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Rate not found with id: "
                                                + rateId
                                ));

        validateRateBelongsToPlan(rate, planId);

        planRateRepository.delete(rate);
    }

    private void validateRateBelongsToPlan(
            PlanRate rate,
            Long planId) {

        if (!rate.getPlan().getPlanId().equals(planId)) {
            throw new ResourceNotFoundException(
                    "Rate " + rate.getRateId() +
                            " does not belong to plan " + planId
            );
        }
    }

    private PlanRateResponse mapToResponse(
            PlanRate rate) {

        return new PlanRateResponse(
                rate.getRateId(),
                rate.getPlan().getPlanId(),
                rate.getRateType(),
                rate.getAmount(),
                rate.getUnit()
        );
    }
}