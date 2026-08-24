package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.SubscriptionRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.SubscriptionResponse;
import com.Telecom.Billing.Telecom_Billing_System.entity.Customer;
import com.Telecom.Billing.Telecom_Billing_System.entity.Plan;
import com.Telecom.Billing.Telecom_Billing_System.entity.Subscription;
import com.Telecom.Billing.Telecom_Billing_System.entity.SubscriptionHistory;
import com.Telecom.Billing.Telecom_Billing_System.exception.ResourceNotFoundException;
import com.Telecom.Billing.Telecom_Billing_System.repository.CustomerRepository;
import com.Telecom.Billing.Telecom_Billing_System.repository.PlanRepository;
import com.Telecom.Billing.Telecom_Billing_System.repository.SubscriptionHistoryRepository;
import com.Telecom.Billing.Telecom_Billing_System.repository.SubscriptionRepository;
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

        Subscription subscription = Subscription.builder()
                .customer(customer)
                .plan(plan)
                .startDate(request.startDate())
                .status("ACTIVE")
                .build();

        Subscription savedSubscription =
                subscriptionRepository.save(subscription);

        SubscriptionHistory history = SubscriptionHistory.builder()
                .subscription(savedSubscription)
                .action("SUBSCRIBED")
                .description("Subscription created")
                .build();

        historyRepository.save(history);

        return mapToResponse(savedSubscription);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionResponse> getAllSubscriptions() {

        return subscriptionRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

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

        subscription.setCustomer(customer);
        subscription.setPlan(plan);
        subscription.setStartDate(request.startDate());

        Subscription updatedSubscription =
                subscriptionRepository.save(subscription);

        return mapToResponse(updatedSubscription);
    }

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

        subscriptionRepository.delete(subscription);
    }

    private SubscriptionResponse mapToResponse(
            Subscription subscription) {

        return new SubscriptionResponse(
                subscription.getSubscriptionId(),
                subscription.getCustomer().getCustomerId(),
                subscription.getPlan().getPlanId(),
                subscription.getStartDate(),
                subscription.getEndDate(),
                subscription.getStatus(),
                subscription.getCreatedAt(),
                subscription.getUpdatedAt()
        );
    }
}