package com.einsurance.service;

import com.einsurance.dto.auth.LoginRequest;
import com.einsurance.dto.auth.LoginResponse;
import com.einsurance.entity.Admin;
import com.einsurance.entity.Customer;
import com.einsurance.entity.Employee;
import com.einsurance.entity.InsuranceAgent;
import com.einsurance.repository.AdminRepository;
import com.einsurance.repository.CustomerRepository;
import com.einsurance.repository.EmployeeRepository;
import com.einsurance.repository.InsuranceAgentRepository;
import com.einsurance.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final InsuranceAgentRepository agentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthServiceImpl(
            AdminRepository adminRepository,
            EmployeeRepository employeeRepository,
            CustomerRepository customerRepository,
            InsuranceAgentRepository agentRepository,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.agentRepository = agentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        String username = request.getUsername();
        String password = request.getPassword();

        var customer = customerRepository.findByUsername(username);

        if (customer.isPresent()
                && passwordEncoder.matches(
                password, customer.get().getPassword())) {

            return createLoginResponse(username, "CUSTOMER");
        }

        var agent = agentRepository.findByUsername(username);

        if (agent.isPresent()
                && passwordEncoder.matches(
                password, agent.get().getPassword())) {

            return createLoginResponse(username, "AGENT");
        }

        var employee = employeeRepository.findByUsername(username);

        if (employee.isPresent()
                && passwordEncoder.matches(
                password, employee.get().getPassword())) {

            return createLoginResponse(username, employee.get().getRole());
        }

        var admin = adminRepository.findByUsername(username);

        if (admin.isPresent()
                && passwordEncoder.matches(
                password, admin.get().getPassword())) {

            return createLoginResponse(username, "ADMIN");
        }

        throw new IllegalArgumentException(
                "Invalid username or password"
        );
    }

    private LoginResponse createLoginResponse(
            String username,
            String role
    ) {

        String token = jwtService.generateToken(username, role);

        return LoginResponse.builder()
                .token(token)
                .username(username)
                .role(role)
                .build();
    }
}