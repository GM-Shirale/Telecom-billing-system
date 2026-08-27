package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.dto.response.SubscriptionHistoryResponse;
import com.Telecom.Billing.Telecom_Billing_System.exception.ResourceNotFoundException;
import com.Telecom.Billing.Telecom_Billing_System.entity.SubscriptionHistory;
import com.Telecom.Billing.Telecom_Billing_System.repository.SubscriptionHistoryRepository;
import com.Telecom.Billing.Telecom_Billing_System.repository.SubscriptionRepository;
import com.Telecom.Billing.Telecom_Billing_System.service.SubscriptionHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionHistoryServiceImpl
        implements SubscriptionHistoryService {

    private final SubscriptionHistoryRepository historyRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<SubscriptionHistoryResponse> getHistory(
            Long subscriptionId) {

        subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Subscription not found with id: "
                                        + subscriptionId
                        ));

        return historyRepository
                .findBySubscriptionSubscriptionIdOrderByChangedAtDesc(
                        subscriptionId
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private SubscriptionHistoryResponse mapToResponse(SubscriptionHistory history) {

        return new SubscriptionHistoryResponse(
                history.getHistoryId(),
                history.getSubscription()
                        .getSubscriptionId(),
                history.getAction(),
                history.getDescription(),
                history.getChangedAt()
        );
    }
}