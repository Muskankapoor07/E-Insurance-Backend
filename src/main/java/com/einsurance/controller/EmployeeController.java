package com.einsurance.controller;

import com.einsurance.dto.employee.EmployeeRequest;
import com.einsurance.dto.employee.EmployeeResponse;
import com.einsurance.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeResponse> registerEmployee(
            @Valid @RequestBody EmployeeRequest request
    ) {
        EmployeeResponse response =
                employeeService.registerEmployee(request);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}