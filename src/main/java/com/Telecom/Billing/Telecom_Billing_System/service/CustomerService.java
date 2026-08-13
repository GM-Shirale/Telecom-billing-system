package com.Telecom.Billing.Telecom_Billing_System.service;


import com.Telecom.Billing.Telecom_Billing_System.dto.request.CustomerRequest;
import com.Telecom.Billing.Telecom_Billing_System.entity.Customer;

import java.util.List;

public interface CustomerService {


    Customer createCustomer(CustomerRequest request);

    Customer getCustomer(Long id);

    List<Customer> getAllCustomers();

    Customer updateCustomer(Long id, CustomerRequest request);

    void deleteCustomer(Long id);
}
