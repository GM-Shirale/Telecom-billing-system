package com.Telecom.Billing.Telecom_Billing_System.controller;

import com.Telecom.Billing.Telecom_Billing_System.dto.response.SubscriptionHistoryResponse;
import com.Telecom.Billing.Telecom_Billing_System.service.SubscriptionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions/{subscriptionId}/history")
@RequiredArgsConstructor
public class SubscriptionHistoryController {

    private final SubscriptionHistoryService historyService;

    @GetMapping
    public List<SubscriptionHistoryResponse> getHistory(
            @PathVariable Long subscriptionId) {

        return historyService.getHistory(subscriptionId);
    }
}