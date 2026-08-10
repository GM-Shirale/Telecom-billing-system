package com.Telecom.Billing.Telecom_Billing_System.service.impl;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.CustomerRequest;
import com.Telecom.Billing.Telecom_Billing_System.entity.Customer;
import com.Telecom.Billing.Telecom_Billing_System.repository.CustomerRepository;
import com.Telecom.Billing.Telecom_Billing_System.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService  {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(CustomerRequest request) {

        Customer customer = Customer.builder()
                .customerCode(generateCustomerCode())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .status("ACTIVE")
                .build();

        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {

        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Customer not found: " + id));
    }

    @Override
    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    private String generateCustomerCode() {

        return "CUS-" + System.currentTimeMillis();
    }
}
