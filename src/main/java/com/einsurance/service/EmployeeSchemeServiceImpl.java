package com.einsurance.service;

import com.einsurance.dto.employeescheme.EmployeeSchemeRequest;
import com.einsurance.dto.employeescheme.EmployeeSchemeResponse;
import com.einsurance.entity.Employee;
import com.einsurance.entity.EmployeeScheme;
import com.einsurance.entity.Scheme;
import com.einsurance.exception.DuplicateResourceException;
import com.einsurance.exception.ResourceNotFoundException;
import com.einsurance.mapper.EmployeeSchemeMapper;
import com.einsurance.repository.EmployeeRepository;
import com.einsurance.repository.EmployeeSchemeRepository;
import com.einsurance.repository.SchemeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EmployeeSchemeServiceImpl implements EmployeeSchemeService {

    private final EmployeeSchemeRepository employeeSchemeRepository;
    private final EmployeeRepository employeeRepository;
    private final SchemeRepository schemeRepository;
    private final EmployeeSchemeMapper employeeSchemeMapper;

    public EmployeeSchemeServiceImpl(EmployeeSchemeRepository employeeSchemeRepository,
                                     EmployeeRepository employeeRepository,
                                     SchemeRepository schemeRepository,
                                     EmployeeSchemeMapper employeeSchemeMapper) {
        this.employeeSchemeRepository = employeeSchemeRepository;
        this.employeeRepository = employeeRepository;
        this.schemeRepository = schemeRepository;
        this.employeeSchemeMapper = employeeSchemeMapper;
    }

    @Override
    public EmployeeSchemeResponse assignSchemeToEmployee(EmployeeSchemeRequest request) {
        if (employeeSchemeRepository.existsByEmployee_EmployeeIdAndScheme_SchemeId(
                request.getEmployeeId(), request.getSchemeId())) {
            throw new DuplicateResourceException("Scheme is already assigned to this Employee");
        }

        Employee employee = employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with ID: " + request.getEmployeeId()));

        Scheme scheme = schemeRepository.findById(request.getSchemeId())
                .orElseThrow(() -> new ResourceNotFoundException("Scheme not found with ID: " + request.getSchemeId()));

        EmployeeScheme employeeScheme = employeeSchemeMapper.toEntity(employee, scheme);
        EmployeeScheme saved = employeeSchemeRepository.save(employeeScheme);
        return employeeSchemeMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeSchemeResponse> getSchemesByEmployeeId(Integer employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found with ID: " + employeeId);
        }
        return employeeSchemeRepository.findByEmployee_EmployeeId(employeeId).stream()
                .map(employeeSchemeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<EmployeeSchemeResponse> getEmployeesBySchemeId(Integer schemeId) {
        if (!schemeRepository.existsById(schemeId)) {
            throw new ResourceNotFoundException("Scheme not found with ID: " + schemeId);
        }
        return employeeSchemeRepository.findByScheme_SchemeId(schemeId).stream()
                .map(employeeSchemeMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void removeSchemeFromEmployee(Integer employeeId, Integer schemeId) {
        if (!employeeSchemeRepository.existsByEmployee_EmployeeIdAndScheme_SchemeId(employeeId, schemeId)) {
            throw new ResourceNotFoundException("Scheme assignment not found for Employee ID " + employeeId + " and Scheme ID " + schemeId);
        }
        employeeSchemeRepository.deleteByEmployee_EmployeeIdAndScheme_SchemeId(employeeId, schemeId);
    }
}
