package com.einsurance.controller;

import com.einsurance.dto.customer.CustomerRequest;
import com.einsurance.dto.customer.CustomerResponse;
import com.einsurance.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

        CustomerResponse response =
                customerService.registerCustomer(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}