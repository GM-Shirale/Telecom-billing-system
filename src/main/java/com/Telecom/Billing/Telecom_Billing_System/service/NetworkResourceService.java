package com.Telecom.Billing.Telecom_Billing_System.service;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.NetworkResourceRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.NetworkResourceResponse;

import java.util.List;

public interface NetworkResourceService {

    NetworkResourceResponse createResource(NetworkResourceRequest request);

    List<NetworkResourceResponse> getAllResources();

    NetworkResourceResponse getResource(Long resourceId);

    NetworkResourceResponse updateResource(Long resourceId, NetworkResourceRequest request);

    void deleteResource(Long resourceId);

}