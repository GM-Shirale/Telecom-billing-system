package com.Telecom.Billing.Telecom_Billing_System.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plan_feature")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long featureId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id", nullable = false)
    private Plan plan;

    @Column(nullable = false, length = 100)
    private String featureName;

    @Column(length = 200)
    private String featureValue;
}