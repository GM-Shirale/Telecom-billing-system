package com.Telecom.Billing.Telecom_Billing_System.controller;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.CustomerAddressRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.CustomerAddressResponse;
import com.Telecom.Billing.Telecom_Billing_System.service.CustomerAddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers/{customerId}/addresses")
@RequiredArgsConstructor
public class CustomerAddressController {

    private final CustomerAddressService customerAddressService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerAddressResponse createAddress(@PathVariable Long customerId, @Valid @RequestBody CustomerAddressRequest request) {

        return customerAddressService.createAddress(customerId, request);
    }

    @GetMapping
    public List<CustomerAddressResponse> getAddresses(
            @PathVariable Long customerId) {

        return customerAddressService
                .getAddressesByCustomerId(customerId);
    }

    @GetMapping("/{addressId}")
    public CustomerAddressResponse getAddress(
            @PathVariable Long customerId,
            @PathVariable Long addressId) {

        return customerAddressService.getAddress(
                customerId,
                addressId
        );
    }
}