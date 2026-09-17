package com.einsurance.service;

import com.einsurance.dto.customer.CustomerRequest;
import com.einsurance.dto.customer.CustomerResponse;

public interface CustomerService {

    CustomerResponse registerCustomer(CustomerRequest request);
}