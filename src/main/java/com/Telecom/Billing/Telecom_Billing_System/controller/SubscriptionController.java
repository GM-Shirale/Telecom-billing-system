package com.Telecom.Billing.Telecom_Billing_System.controller;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.SubscriptionRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.SubscriptionResponse;
import com.Telecom.Billing.Telecom_Billing_System.service.SubscriptionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubscriptionResponse createSubscription(
            @Valid @RequestBody SubscriptionRequest request) {

        return subscriptionService.createSubscription(request);
    }

    @GetMapping
    public List<SubscriptionResponse> getAllSubscriptions() {

        return subscriptionService.getAllSubscriptions();
    }

    @GetMapping("/{subscriptionId}")
    public SubscriptionResponse getSubscriptionById(
            @PathVariable Long subscriptionId) {

        return subscriptionService.getSubscriptionById(
                subscriptionId
        );
    }

    @GetMapping("/customer/{customerId}")
    public List<SubscriptionResponse>
    getSubscriptionsByCustomerId(
            @PathVariable Long customerId) {

        return subscriptionService
                .getSubscriptionsByCustomerId(customerId);
    }

    @PutMapping("/{subscriptionId}")
    public SubscriptionResponse updateSubscription(
            @PathVariable Long subscriptionId,
            @Valid @RequestBody SubscriptionRequest request) {

        return subscriptionService.updateSubscription(
                subscriptionId,
                request
        );
    }

    @DeleteMapping("/{subscriptionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubscription(
            @PathVariable Long subscriptionId) {

        subscriptionService.deleteSubscription(
                subscriptionId
        );
    }
}