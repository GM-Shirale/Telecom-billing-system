package com.Telecom.Billing.Telecom_Billing_System.service;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.SIMRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.SIMResponse;

import java.util.List;

public interface SIMService {

    SIMResponse createSIM(SIMRequest request);

    List<SIMResponse> getAllSIMs();

    SIMResponse getSIM(Long simId);

    SIMResponse updateSIM(Long simId, SIMRequest request);

    void deleteSIM(Long simId);
}