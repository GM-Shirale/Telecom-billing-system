package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.FamilyMemberRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.FamilyMemberResponse;
import com.Telecom.Billing.Telecom_Billing_System.entity.FamilyMember;
import com.Telecom.Billing.Telecom_Billing_System.entity.Plan;
import com.Telecom.Billing.Telecom_Billing_System.entity.Subscription;
import com.Telecom.Billing.Telecom_Billing_System.enums.ServiceType;
import com.Telecom.Billing.Telecom_Billing_System.exception.ResourceNotFoundException;
import com.Telecom.Billing.Telecom_Billing_System.repository.FamilyMemberRepository;
import com.Telecom.Billing.Telecom_Billing_System.repository.SubscriptionRepository;
import com.Telecom.Billing.Telecom_Billing_System.service.FamilyMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FamilyMemberServiceImpl
        implements FamilyMemberService {

    private final FamilyMemberRepository familyMemberRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Override
    @Transactional
    public FamilyMemberResponse addMember(
            Long subscriptionId,
            FamilyMemberRequest request) {

        Subscription subscription =
                subscriptionRepository.findById(subscriptionId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Subscription not found with id: "
                                                + subscriptionId
                                ));

        Plan plan = subscription.getPlan();

        if (plan.getServiceType() != ServiceType.FAMILY) {
            throw new IllegalArgumentException(
                    "Family members can only be added to FAMILY plans"
            );
        }
        // 2. Maximum 4 family members
        List<FamilyMember> existingMembers =
                familyMemberRepository
                        .findBySubscriptionSubscriptionId(subscriptionId);

        if (existingMembers.size() >= 4) {
            throw new IllegalArgumentException(
                    "Maximum 4 family members are allowed"
            );
        }


        if (familyMemberRepository.existsByMemberPhone(request.memberPhone())) {

            throw new IllegalArgumentException(
                    "Family member with phone "
                            + request.memberPhone()
                            + " already exists"
            );
        }

        FamilyMember member = FamilyMember.builder()
                .subscription(subscription)
                .memberName(request.memberName())
                .memberPhone(request.memberPhone())
                .relationship(request.relationship())
                .status("ACTIVE")
                .build();

        FamilyMember savedMember =
                familyMemberRepository.save(member);

        return mapToResponse(savedMember);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FamilyMemberResponse> getMembers(
            Long subscriptionId) {

        subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Subscription not found with id: "
                                        + subscriptionId
                        ));

        return familyMemberRepository
                .findBySubscriptionSubscriptionId(subscriptionId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public FamilyMemberResponse getMember(
            Long familyMemberId) {

        FamilyMember member =
                familyMemberRepository.findById(familyMemberId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Family member not found with id: "
                                                + familyMemberId
                                ));

        return mapToResponse(member);
    }

    @Override
    @Transactional
    public void deleteMember(Long familyMemberId) {

        FamilyMember member =
                familyMemberRepository.findById(familyMemberId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Family member not found with id: "
                                                + familyMemberId
                                ));

        familyMemberRepository.delete(member);
    }

    private FamilyMemberResponse mapToResponse(FamilyMember member) {

        return new FamilyMemberResponse(
                member.getFamilyMemberId(),
                member.getSubscription()
                        .getSubscriptionId(),
                member.getMemberName(),
                member.getMemberPhone(),
                member.getRelationship(),
                member.getStatus(),
                member.getCreatedAt(),
                member.getUpdatedAt()
        );
    }
}