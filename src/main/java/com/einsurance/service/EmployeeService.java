package com.einsurance.service;

import com.einsurance.dto.employee.EmployeeRequest;
import com.einsurance.dto.employee.EmployeeResponse;

public interface EmployeeService {

    EmployeeResponse registerEmployee(EmployeeRequest request);
}