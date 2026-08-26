package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.SubscriptionRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.SubscriptionResponse;
import com.Telecom.Billing.Telecom_Billing_System.entity.*;
import com.Telecom.Billing.Telecom_Billing_System.exception.ResourceNotFoundException;
import com.Telecom.Billing.Telecom_Billing_System.repository.*;
import com.Telecom.Billing.Telecom_Billing_System.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl
        implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final CustomerRepository customerRepository;
    private final PlanRepository planRepository;
    private final SubscriptionHistoryRepository historyRepository;
    private final SIMRepository simRepository;

    // =========================================================
    // CREATE SUBSCRIPTION
    // =========================================================

    @Override
    @Transactional
    public SubscriptionResponse createSubscription(
            SubscriptionRequest request) {

        Customer customer = customerRepository.findById(
                request.customerId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Customer not found with id: "
                                + request.customerId()
                ));

        Plan plan = planRepository.findById(
                request.planId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Plan not found with id: "
                                + request.planId()
                ));

        SIM sim = simRepository.findById(
                request.simId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "SIM not found with id: "
                                + request.simId()
                ));

        // SIM must be available
        if (!"AVAILABLE".equalsIgnoreCase(sim.getStatus())) {

            throw new IllegalArgumentException(
                    "SIM is not available"
            );
        }

        Subscription subscription = Subscription.builder()
                .customer(customer)
                .plan(plan)
                .sim(sim)
                .startDate(request.startDate())
                .status("ACTIVE")
                .build();

        // Activate SIM
        sim.setStatus("ACTIVE");

        Subscription savedSubscription =
                subscriptionRepository.save(subscription);

        SubscriptionHistory history =
                SubscriptionHistory.builder()
                        .subscription(savedSubscription)
                        .action("SUBSCRIBED")
                        .description(
                                "Subscription created with SIM: "
                                        + sim.getMsisdn()
                        )
                        .build();

        historyRepository.save(history);

        return mapToResponse(savedSubscription);
    }

    // =========================================================
    // GET ALL SUBSCRIPTIONS
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponse> getAllSubscriptions() {

        return subscriptionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================================
    // GET SUBSCRIPTION BY ID
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public SubscriptionResponse getSubscriptionById(
            Long subscriptionId) {

        Subscription subscription =
                subscriptionRepository.findById(subscriptionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subscription not found with id: "
                                                + subscriptionId
                                ));

        return mapToResponse(subscription);
    }

    // =========================================================
    // GET SUBSCRIPTIONS BY CUSTOMER
    // =========================================================

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponse>
    getSubscriptionsByCustomerId(Long customerId) {

        customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: "
                                        + customerId
                        ));

        return subscriptionRepository
                .findByCustomerCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =========================================================
    // UPDATE SUBSCRIPTION
    // =========================================================

    @Override
    @Transactional
    public SubscriptionResponse updateSubscription(
            Long subscriptionId,
            SubscriptionRequest request) {

        Subscription subscription =
                subscriptionRepository.findById(subscriptionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subscription not found with id: "
                                                + subscriptionId
                                ));

        Customer customer = customerRepository.findById(
                request.customerId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Customer not found with id: "
                                + request.customerId()
                ));

        Plan plan = planRepository.findById(
                request.planId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "Plan not found with id: "
                                + request.planId()
                ));

        SIM oldSim = subscription.getSim();

        SIM newSim = simRepository.findById(
                request.simId()
        ).orElseThrow(() ->
                new ResourceNotFoundException(
                        "SIM not found with id: "
                                + request.simId()
                ));

        // If user selects a different SIM
        if (oldSim == null
                || !oldSim.getSimId()
                .equals(newSim.getSimId())) {

            // New SIM must be available
            if (!"AVAILABLE".equalsIgnoreCase(
                    newSim.getStatus())) {

                throw new IllegalArgumentException(
                        "New SIM is not available"
                );
            }

            // Release old SIM
            if (oldSim != null) {
                oldSim.setStatus("AVAILABLE");
            }

            // Activate new SIM
            newSim.setStatus("ACTIVE");

            subscription.setSim(newSim);
        }

        subscription.setCustomer(customer);
        subscription.setPlan(plan);
        subscription.setStartDate(request.startDate());

        Subscription updatedSubscription =
                subscriptionRepository.save(subscription);

        SubscriptionHistory history =
                SubscriptionHistory.builder()
                        .subscription(updatedSubscription)
                        .action("UPDATED")
                        .description(
                                "Subscription details updated"
                        )
                        .build();

        historyRepository.save(history);

        return mapToResponse(updatedSubscription);
    }

    // =========================================================
    // DELETE SUBSCRIPTION
    // =========================================================

    @Override
    @Transactional
    public void deleteSubscription(Long subscriptionId) {

        Subscription subscription =
                subscriptionRepository.findById(subscriptionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subscription not found with id: "
                                                + subscriptionId
                                ));

        // Release SIM
        SIM sim = subscription.getSim();

        if (sim != null) {
            sim.setStatus("AVAILABLE");
        }

        subscriptionRepository.delete(subscription);
    }

    // =========================================================
    // MAP ENTITY → RESPONSE
    // =========================================================

    private SubscriptionResponse mapToResponse(
            Subscription subscription) {

        SIM sim = subscription.getSim();

        return new SubscriptionResponse(
                subscription.getSubscriptionId(),

                subscription.getCustomer()
                        .getCustomerId(),

                subscription.getPlan()
                        .getPlanId(),

                sim != null
                        ? sim.getSimId()
                        : null,

                sim != null
                        ? sim.getMsisdn()
                        : null,

                subscription.getStartDate(),

                subscription.getEndDate(),

                subscription.getStatus(),

                subscription.getCreatedAt(),

                subscription.getUpdatedAt()
        );
    }
}