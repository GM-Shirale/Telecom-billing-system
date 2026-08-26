package com.Telecom.Billing.Telecom_Billing_System.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "number_portability")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NumberPortability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long portId;

    @Column(nullable = false, length = 20)
    private String msisdn;

    @Column(nullable = false, length = 80)
    private String oldProvider;

    @Column(nullable = false, length = 80)
    private String newProvider;

    @Column(nullable = false, length = 30)
    private String status;

    private LocalDateTime portRequestDate;

    private LocalDateTime portDate;

    @Column(length = 255)
    private String reason;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {

        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();

        if (portRequestDate == null) {
            portRequestDate = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void onUpdate() {

        updatedAt = LocalDateTime.now();
    }
}