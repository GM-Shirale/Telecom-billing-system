package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.PlanFeatureRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.PlanFeatureResponse;
import com.Telecom.Billing.Telecom_Billing_System.entity.Plan;
import com.Telecom.Billing.Telecom_Billing_System.entity.PlanFeature;
import com.Telecom.Billing.Telecom_Billing_System.exception.ResourceNotFoundException;
import com.Telecom.Billing.Telecom_Billing_System.repository.PlanFeatureRepository;
import com.Telecom.Billing.Telecom_Billing_System.repository.PlanRepository;
import com.Telecom.Billing.Telecom_Billing_System.service.PlanFeatureService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanFeatureServiceImpl
        implements PlanFeatureService {

    private final PlanRepository planRepository;
    private final PlanFeatureRepository planFeatureRepository;

    @Override
    @Transactional
    public PlanFeatureResponse createFeature(
            Long planId,
            PlanFeatureRequest request) {

        Plan plan = planRepository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plan not found with id: " + planId
                        ));

        PlanFeature feature = PlanFeature.builder()
                .plan(plan)
                .featureName(request.featureName())
                .featureValue(request.featureValue())
                .build();

        PlanFeature savedFeature =
                planFeatureRepository.save(feature);

        return mapToResponse(savedFeature);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PlanFeatureResponse> getFeaturesByPlanId(
            Long planId) {

        planRepository.findById(planId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Plan not found with id: " + planId
                        ));

        return planFeatureRepository
                .findByPlanPlanId(planId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PlanFeatureResponse getFeature(
            Long planId,
            Long featureId) {

        PlanFeature feature =
                planFeatureRepository.findById(featureId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Feature not found with id: "
                                                + featureId
                                ));

        if (!feature.getPlan().getPlanId().equals(planId)) {
            throw new ResourceNotFoundException(
                    "Feature " + featureId +
                            " does not belong to plan " + planId
            );
        }

        return mapToResponse(feature);
    }

    @Override
    @Transactional
    public PlanFeatureResponse updateFeature(
            Long planId,
            Long featureId,
            PlanFeatureRequest request) {

        PlanFeature feature =
                planFeatureRepository.findById(featureId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Feature not found with id: "
                                                + featureId
                                ));

        if (!feature.getPlan().getPlanId().equals(planId)) {
            throw new ResourceNotFoundException(
                    "Feature " + featureId +
                            " does not belong to plan " + planId
            );
        }

        feature.setFeatureName(request.featureName());
        feature.setFeatureValue(request.featureValue());

        PlanFeature updatedFeature =
                planFeatureRepository.save(feature);

        return mapToResponse(updatedFeature);
    }

    @Override
    @Transactional
    public void deleteFeature(
            Long planId,
            Long featureId) {

        PlanFeature feature =
                planFeatureRepository.findById(featureId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Feature not found with id: "
                                                + featureId
                                ));

        if (!feature.getPlan().getPlanId().equals(planId)) {
            throw new ResourceNotFoundException(
                    "Feature " + featureId +
                            " does not belong to plan " + planId
            );
        }

        planFeatureRepository.delete(feature);
    }

    private PlanFeatureResponse mapToResponse(
            PlanFeature feature) {

        return new PlanFeatureResponse(
                feature.getFeatureId(),
                feature.getPlan().getPlanId(),
                feature.getFeatureName(),
                feature.getFeatureValue()
        );
    }
}