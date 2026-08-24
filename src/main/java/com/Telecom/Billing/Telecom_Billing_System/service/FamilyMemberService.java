package com.Telecom.Billing.Telecom_Billing_System.service;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.FamilyMemberRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.FamilyMemberResponse;

import java.util.List;

public interface FamilyMemberService {

    FamilyMemberResponse addMember(
            Long subscriptionId,
            FamilyMemberRequest request
    );

    List<FamilyMemberResponse> getMembers(
            Long subscriptionId
    );

    FamilyMemberResponse getMember(
            Long familyMemberId
    );

    void deleteMember(
            Long familyMemberId
    );
}