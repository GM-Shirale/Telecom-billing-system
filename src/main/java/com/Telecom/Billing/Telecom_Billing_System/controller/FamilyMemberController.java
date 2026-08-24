package com.Telecom.Billing.Telecom_Billing_System.controller;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.FamilyMemberRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.FamilyMemberResponse;
import com.Telecom.Billing.Telecom_Billing_System.service.FamilyMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions/{subscriptionId}/family-members")
@RequiredArgsConstructor
public class FamilyMemberController {

    private final FamilyMemberService familyMemberService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FamilyMemberResponse addMember(
            @PathVariable Long subscriptionId,
            @Valid @RequestBody FamilyMemberRequest request) {

        return familyMemberService.addMember(
                subscriptionId,
                request
        );
    }

    @GetMapping
    public List<FamilyMemberResponse> getMembers(
            @PathVariable Long subscriptionId) {

        return familyMemberService.getMembers(
                subscriptionId
        );
    }

    @GetMapping("/{familyMemberId}")
    public FamilyMemberResponse getMember(
            @PathVariable Long familyMemberId) {

        return familyMemberService.getMember(
                familyMemberId
        );
    }

    @DeleteMapping("/{familyMemberId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMember(
            @PathVariable Long familyMemberId) {

        familyMemberService.deleteMember(
                familyMemberId
        );
    }
}