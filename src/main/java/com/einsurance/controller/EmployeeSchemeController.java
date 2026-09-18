package com.einsurance.controller;

import com.einsurance.dto.employeescheme.EmployeeSchemeRequest;
import com.einsurance.dto.employeescheme.EmployeeSchemeResponse;
import com.einsurance.service.EmployeeSchemeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employee-schemes")
public class EmployeeSchemeController {

    private final EmployeeSchemeService employeeSchemeService;

    public EmployeeSchemeController(EmployeeSchemeService employeeSchemeService) {
        this.employeeSchemeService = employeeSchemeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeSchemeResponse> assignScheme(
            @Valid @RequestBody EmployeeSchemeRequest request) {
        EmployeeSchemeResponse response = employeeSchemeService.assignSchemeToEmployee(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<EmployeeSchemeResponse>> getSchemesByEmployeeId(
            @PathVariable Integer employeeId) {
        List<EmployeeSchemeResponse> response = employeeSchemeService.getSchemesByEmployeeId(employeeId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/scheme/{schemeId}")
    public ResponseEntity<List<EmployeeSchemeResponse>> getEmployeesBySchemeId(
            @PathVariable Integer schemeId) {
        List<EmployeeSchemeResponse> response = employeeSchemeService.getEmployeesBySchemeId(schemeId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/employee/{employeeId}/scheme/{schemeId}")
    public ResponseEntity<Void> removeScheme(
            @PathVariable Integer employeeId,
            @PathVariable Integer schemeId) {
        employeeSchemeService.removeSchemeFromEmployee(employeeId, schemeId);
        return ResponseEntity.noContent().build();
    }
}
