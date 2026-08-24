package com.Telecom.Billing.Telecom_Billing_System.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "subscription_history")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubscriptionHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", nullable = false)
    private Subscription subscription;

    @Column(nullable = false, length = 30)
    private String action;

    @Column(length = 500)
    private String description;

    private LocalDateTime changedAt;

    @PrePersist
    public void onCreate() {
        changedAt = LocalDateTime.now();
    }
}