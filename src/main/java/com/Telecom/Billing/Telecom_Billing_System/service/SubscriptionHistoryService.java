package com.Telecom.Billing.Telecom_Billing_System.service;

import com.Telecom.Billing.Telecom_Billing_System.dto.response.SubscriptionHistoryResponse;

import java.util.List;

public interface SubscriptionHistoryService {

    List<SubscriptionHistoryResponse> getHistory(Long subscriptionId);

}