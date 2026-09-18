package com.einsurance.service;

import com.einsurance.dto.customer.CustomerRequest;
import com.einsurance.dto.customer.CustomerResponse;
import com.einsurance.entity.Customer;
import com.einsurance.entity.InsuranceAgent;
import com.einsurance.exception.DuplicateResourceException;
import com.einsurance.exception.ResourceNotFoundException;
import com.einsurance.mapper.CustomerMapper;
import com.einsurance.repository.CustomerRepository;
import com.einsurance.repository.InsuranceAgentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final InsuranceAgentRepository agentRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            InsuranceAgentRepository agentRepository,
            CustomerMapper customerMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.customerRepository = customerRepository;
        this.agentRepository = agentRepository;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CustomerResponse registerCustomer(CustomerRequest request) {

        if (customerRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new DuplicateResourceException("Username already exists: " + request.getUsername());
        }

        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already exists: " + request.getEmail());
        }

        Customer customer = customerMapper.toEntity(request);

        customer.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerByUsername(String username) {
        Customer customer = customerRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with username: " + username));
        return customerMapper.toResponse(customer);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getAllCustomers(Pageable pageable) {
        return customerRepository.findAll(pageable)
                .map(customerMapper::toResponse);
    }

    @Override
    public CustomerResponse updateCustomer(Integer customerId, CustomerRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        if (!customer.getEmail().equalsIgnoreCase(request.getEmail()) &&
                customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Email already in use: " + request.getEmail());
        }

        customerMapper.updateEntity(customer, request);
        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            customer.setPassword(passwordEncoder.encode(request.getPassword()));
        }

        Customer updated = customerRepository.save(customer);
        return customerMapper.toResponse(updated);
    }

    @Override
    public void deleteCustomer(Integer customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));
        customerRepository.delete(customer);
    }

    @Override
    public CustomerResponse assignAgent(Integer customerId, Integer agentId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with ID: " + customerId));

        InsuranceAgent agent = agentRepository.findById(agentId)
                .orElseThrow(() -> new ResourceNotFoundException("Insurance Agent not found with ID: " + agentId));

        customer.setInsuranceAgent(agent);
        Customer updated = customerRepository.save(customer);
        return customerMapper.toResponse(updated);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CustomerResponse> getCustomersByAgentId(Integer agentId, Pageable pageable) {
        if (!agentRepository.existsById(agentId)) {
            throw new ResourceNotFoundException("Insurance Agent not found with ID: " + agentId);
        }
        return customerRepository.findByInsuranceAgent_AgentId(agentId, pageable)
                .map(customerMapper::toResponse);
    }
}