package com.einsurance.service;

import com.einsurance.dto.customer.CustomerRequest;
import com.einsurance.dto.customer.CustomerResponse;
import com.einsurance.entity.Customer;
import com.einsurance.mapper.CustomerMapper;
import com.einsurance.repository.CustomerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(
            CustomerRepository customerRepository,
            CustomerMapper customerMapper,
            PasswordEncoder passwordEncoder
    ) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CustomerResponse registerCustomer(CustomerRequest request) {

        if (customerRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (customerRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        Customer customer = customerMapper.toEntity(request);

        customer.setPassword(
                passwordEncoder.encode(request.getPassword())
        );

        Customer savedCustomer = customerRepository.save(customer);

        return customerMapper.toResponse(savedCustomer);
    }
}