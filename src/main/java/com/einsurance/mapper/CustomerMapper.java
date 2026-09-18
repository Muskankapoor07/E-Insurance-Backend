package com.einsurance.mapper;

import com.einsurance.dto.customer.CustomerRequest;
import com.einsurance.dto.customer.CustomerResponse;
import com.einsurance.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public Customer toEntity(CustomerRequest request) {

        return Customer.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phone(request.getPhone())
                .dateOfBirth(request.getDateOfBirth())
                .build();
    }

    public CustomerResponse toResponse(Customer customer) {

        Integer agentId = customer.getInsuranceAgent() != null
                ? customer.getInsuranceAgent().getAgentId()
                : null;

        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .username(customer.getUsername())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .dateOfBirth(customer.getDateOfBirth())
                .agentId(agentId)
                .createdAt(customer.getCreatedAt())
                .build();
    }

    public void updateEntity(Customer customer, CustomerRequest request) {
        customer.setFullName(request.getFullName());
        customer.setEmail(request.getEmail());
        customer.setPhone(request.getPhone());
        customer.setDateOfBirth(request.getDateOfBirth());
    }
}