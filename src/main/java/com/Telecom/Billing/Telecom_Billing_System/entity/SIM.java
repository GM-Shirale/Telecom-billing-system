package com.Telecom.Billing.Telecom_Billing_System.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "sim")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SIM {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long simId;

    @Column(nullable = false, unique = true, length = 25)
    private String iccid;

    @Column(nullable = false, unique = true, length = 20)
    private String msisdn;

    @Column(nullable = false, length = 20)
    private String status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "network_resource_id")
    private NetworkResource networkResource;

    private LocalDateTime activatedAt;

    private LocalDateTime deactivatedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}