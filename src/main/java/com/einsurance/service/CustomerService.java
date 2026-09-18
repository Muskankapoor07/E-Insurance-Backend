package com.einsurance.service;

import com.einsurance.dto.customer.CustomerRequest;
import com.einsurance.dto.customer.CustomerResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomerService {

    CustomerResponse registerCustomer(CustomerRequest request);
    CustomerResponse getCustomerById(Integer customerId);
    CustomerResponse getCustomerByUsername(String username);
    Page<CustomerResponse> getAllCustomers(Pageable pageable);
    CustomerResponse updateCustomer(Integer customerId, CustomerRequest request);
    void deleteCustomer(Integer customerId);
    CustomerResponse assignAgent(Integer customerId, Integer agentId);
    Page<CustomerResponse> getCustomersByAgentId(Integer agentId, Pageable pageable);
}