package com.Telecom.Billing.Telecom_Billing_System.service;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.CustomerAddressRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.CustomerAddressResponse;

import java.util.List;

public interface CustomerAddressService {

    CustomerAddressResponse createAddress(Long customerId, CustomerAddressRequest request);

    List<CustomerAddressResponse> getAddressesByCustomerId(Long customerId);

    CustomerAddressResponse getAddress(Long customerId, Long addressId);

}
