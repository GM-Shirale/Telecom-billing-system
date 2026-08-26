package com.Telecom.Billing.Telecom_Billing_System.service;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.NumberPortabilityRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.NumberPortabilityResponse;

import java.util.List;

public interface NumberPortabilityService {

    NumberPortabilityResponse requestPort(
            NumberPortabilityRequest request);

    List<NumberPortabilityResponse> getAllPortRequests();

    NumberPortabilityResponse getPortRequest(Long portId);

    NumberPortabilityResponse approvePort(Long portId);

    NumberPortabilityResponse completePort(Long portId);

    NumberPortabilityResponse rejectPort(
            Long portId,
            String reason);
}