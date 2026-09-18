package com.einsurance.service;

import com.einsurance.dto.employee.EmployeeRequest;
import com.einsurance.dto.employee.EmployeeResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EmployeeService {

    EmployeeResponse registerEmployee(EmployeeRequest request);
    EmployeeResponse getEmployeeById(Integer employeeId);
    Page<EmployeeResponse> getAllEmployees(Pageable pageable);
    EmployeeResponse updateEmployee(Integer employeeId, EmployeeRequest request);
    void deleteEmployee(Integer employeeId);
}