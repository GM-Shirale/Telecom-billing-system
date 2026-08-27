package com.Telecom.Billing.Telecom_Billing_System.service;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.SubscriptionRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {

    SubscriptionResponse createSubscription(
            SubscriptionRequest request
    );

    List<SubscriptionResponse> getAllSubscriptions();

    SubscriptionResponse getSubscriptionById(
            Long subscriptionId
    );

    List<SubscriptionResponse> getSubscriptionsByCustomerId(
            Long customerId
    );

    SubscriptionResponse updateSubscription(
            Long subscriptionId,
            SubscriptionRequest request
    );

    void deleteSubscription(Long subscriptionId);
}