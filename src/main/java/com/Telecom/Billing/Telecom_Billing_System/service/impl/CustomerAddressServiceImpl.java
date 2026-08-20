package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.CustomerAddressRequest;
import com.Telecom.Billing.Telecom_Billing_System.dto.response.CustomerAddressResponse;
import com.Telecom.Billing.Telecom_Billing_System.entity.Customer;
import com.Telecom.Billing.Telecom_Billing_System.entity.CustomerAddress;
import com.Telecom.Billing.Telecom_Billing_System.exception.ResourceNotFoundException;
import com.Telecom.Billing.Telecom_Billing_System.repository.CustomerAddressRepository;
import com.Telecom.Billing.Telecom_Billing_System.repository.CustomerRepository;
import com.Telecom.Billing.Telecom_Billing_System.service.CustomerAddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerAddressServiceImpl
        implements CustomerAddressService {

    private final CustomerRepository customerRepository;
    private final CustomerAddressRepository customerAddressRepository;

    // POST
    @Override
    @Transactional
    public CustomerAddressResponse createAddress(
            Long customerId,
            CustomerAddressRequest request) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + customerId
                        ));

        CustomerAddress address = CustomerAddress.builder()
                .customer(customer)
                .addressType(request.addressType())
                .addressLine(request.addressLine())
                .city(request.city())
                .state(request.state())
                .postalCode(request.postalCode())
                .country(request.country())
                .build();

        CustomerAddress savedAddress =
                customerAddressRepository.save(address);

        return new CustomerAddressResponse(
                savedAddress.getAddressId(),
                customer.getCustomerId(),
                savedAddress.getAddressType(),
                savedAddress.getAddressLine(),
                savedAddress.getCity(),
                savedAddress.getState(),
                savedAddress.getPostalCode(),
                savedAddress.getCountry()
        );
    }

    // GET ALL ADDRESSES OF CUSTOMER
    @Override
    @Transactional(readOnly = true)
    public List<CustomerAddressResponse> getAddressesByCustomerId(
            Long customerId) {

        customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + customerId
                        ));

        List<CustomerAddress> addresses =
                customerAddressRepository
                        .findByCustomerCustomerId(customerId);

        return addresses.stream()
                .map(address -> new CustomerAddressResponse(
                        address.getAddressId(),
                        customerId,
                        address.getAddressType(),
                        address.getAddressLine(),
                        address.getCity(),
                        address.getState(),
                        address.getPostalCode(),
                        address.getCountry()
                ))
                .toList();
    }

    // GET SINGLE ADDRESS
    @Override
    @Transactional(readOnly = true)
    public CustomerAddressResponse getAddress(
            Long customerId,
            Long addressId) {

        customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + customerId
                        ));

        CustomerAddress address =
                customerAddressRepository.findById(addressId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Address not found with id: " + addressId
                                ));

        if (!address.getCustomer()
                .getCustomerId()
                .equals(customerId)) {

            throw new ResourceNotFoundException(
                    "Address " + addressId +
                            " does not belong to customer " + customerId
            );
        }

        return new CustomerAddressResponse(
                address.getAddressId(),
                customerId,
                address.getAddressType(),
                address.getAddressLine(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry()
        );
    }
    @Override
    @Transactional
    public CustomerAddressResponse updateAddress(Long customerId, Long addressId, CustomerAddressRequest request) {

        // Check customer
        customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + customerId
                        ));

        // Find address
        CustomerAddress address =
                customerAddressRepository.findById(addressId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Address not found with id: " + addressId
                                ));

        // Check address belongs to customer
        if (!address.getCustomer()
                .getCustomerId()
                .equals(customerId)) {

            throw new ResourceNotFoundException(
                    "Address " + addressId +
                            " does not belong to customer " + customerId
            );
        }

        // Update managed entity
        address.setAddressType(request.addressType());
        address.setAddressLine(request.addressLine());
        address.setCity(request.city());
        address.setState(request.state());
        address.setPostalCode(request.postalCode());
        address.setCountry(request.country());

        return new CustomerAddressResponse(
                address.getAddressId(),
                customerId,
                address.getAddressType(),
                address.getAddressLine(),
                address.getCity(),
                address.getState(),
                address.getPostalCode(),
                address.getCountry()
        );
    }

    @Override
    @Transactional
    public void deleteAddress(Long customerId, Long addressId) {

        // Check customer
        customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + customerId
                        ));

        // Find address
        CustomerAddress address =
                customerAddressRepository.findById(addressId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Address not found with id: " + addressId
                                ));

        // Make sure address belongs to customer
        if (!address.getCustomer()
                .getCustomerId()
                .equals(customerId)) {

            throw new ResourceNotFoundException(
                    "Address " + addressId +
                            " does not belong to customer " + customerId
            );
        }

        customerAddressRepository.delete(address);
    }

}