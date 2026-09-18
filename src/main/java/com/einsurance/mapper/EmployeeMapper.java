package com.einsurance.mapper;

import com.einsurance.dto.employee.EmployeeRequest;
import com.einsurance.dto.employee.EmployeeResponse;
import com.einsurance.entity.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    public Employee toEntity(EmployeeRequest request) {

        return Employee.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .email(request.getEmail())
                .fullName(request.getFullName())
                .role("EMPLOYEE")
                .build();
    }

    public EmployeeResponse toResponse(Employee employee) {

        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .username(employee.getUsername())
                .email(employee.getEmail())
                .fullName(employee.getFullName())
                .role(employee.getRole())
                .createdAt(employee.getCreatedAt())
                .build();
    }

    public void updateEntity(Employee employee, EmployeeRequest request) {
        employee.setFullName(request.getFullName());
        employee.setEmail(request.getEmail());
    }
}