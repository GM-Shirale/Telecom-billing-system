package com.Telecom.Billing.Telecom_Billing_System.controller;

import com.Telecom.Billing.Telecom_Billing_System.dto.request.CustomerRequest;
import com.Telecom.Billing.Telecom_Billing_System.entity.Customer;
import com.Telecom.Billing.Telecom_Billing_System.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Customer createCustomer(@Valid @RequestBody CustomerRequest request) {

        return customerService.createCustomer(request);
    }

    @GetMapping("/{id}")
    public Customer getCustomer(@PathVariable Long id) {

        return customerService.getCustomer(id);
    }

    @GetMapping
    public List<Customer> getAllCustomers() {

        return customerService.getAllCustomers();
    }
}
