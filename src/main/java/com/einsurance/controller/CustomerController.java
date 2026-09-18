package com.einsurance.controller;

import com.einsurance.dto.customer.CustomerRequest;
import com.einsurance.dto.customer.CustomerResponse;
import com.einsurance.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<CustomerResponse> registerCustomer(
            @Valid @RequestBody CustomerRequest request
    ) {
        CustomerResponse response = customerService.registerCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Admin creates customer
    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest request
    ) {
        CustomerResponse response = customerService.registerCustomer(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Customer views own profile
    @GetMapping("/profile")
    public ResponseEntity<CustomerResponse> getMyProfile(Authentication authentication) {
        String username = authentication != null ? authentication.getName() : null;
        CustomerResponse response = customerService.getCustomerByUsername(username);
        return ResponseEntity.ok(response);
    }

    // Admin gets customer by ID
    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable Integer customerId) {
        CustomerResponse response = customerService.getCustomerById(customerId);
        return ResponseEntity.ok(response);
    }

    // Admin gets all customers with pagination
    @GetMapping
    public ResponseEntity<Page<CustomerResponse>> getAllCustomers(Pageable pageable) {
        Page<CustomerResponse> response = customerService.getAllCustomers(pageable);
        return ResponseEntity.ok(response);
    }

    // Get customers by agent ID
    @GetMapping("/agent/{agentId}")
    public ResponseEntity<Page<CustomerResponse>> getCustomersByAgent(
            @PathVariable Integer agentId,
            Pageable pageable) {
        Page<CustomerResponse> response = customerService.getCustomersByAgentId(agentId, pageable);
        return ResponseEntity.ok(response);
    }

    // Update customer
    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Integer customerId,
            @Valid @RequestBody CustomerRequest request) {
        CustomerResponse response = customerService.updateCustomer(customerId, request);
        return ResponseEntity.ok(response);
    }

    // Delete customer
    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Integer customerId) {
        customerService.deleteCustomer(customerId);
        return ResponseEntity.noContent().build();
    }

    // Assign agent to customer
    @PatchMapping("/{customerId}/assign-agent/{agentId}")
    public ResponseEntity<CustomerResponse> assignAgent(
            @PathVariable Integer customerId,
            @PathVariable Integer agentId) {
        CustomerResponse response = customerService.assignAgent(customerId, agentId);
        return ResponseEntity.ok(response);
    }
}