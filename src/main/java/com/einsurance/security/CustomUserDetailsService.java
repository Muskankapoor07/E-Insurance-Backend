package com.einsurance.security;

import com.einsurance.entity.Admin;
import com.einsurance.entity.Customer;
import com.einsurance.entity.Employee;
import com.einsurance.entity.InsuranceAgent;
import com.einsurance.repository.AdminRepository;
import com.einsurance.repository.CustomerRepository;
import com.einsurance.repository.EmployeeRepository;
import com.einsurance.repository.InsuranceAgentRepository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;
    private final EmployeeRepository employeeRepository;
    private final InsuranceAgentRepository insuranceAgentRepository;
    private final CustomerRepository customerRepository;

    public CustomUserDetailsService(
            AdminRepository adminRepository,
            EmployeeRepository employeeRepository,
            InsuranceAgentRepository insuranceAgentRepository,
            CustomerRepository customerRepository
    ) {
        this.adminRepository = adminRepository;
        this.employeeRepository = employeeRepository;
        this.insuranceAgentRepository = insuranceAgentRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        Admin admin = adminRepository.findByUsername(username)
                .orElse(null);

        if (admin != null) {
            return createUserDetails(
                    admin.getUsername(),
                    admin.getPassword(),
                    "ADMIN"
            );
        }

        Employee employee = employeeRepository.findByUsername(username)
                .orElse(null);

        if (employee != null) {
            return createUserDetails(
                    employee.getUsername(),
                    employee.getPassword(),
                    employee.getRole()
            );
        }

        InsuranceAgent agent =
                insuranceAgentRepository.findByUsername(username)
                        .orElse(null);

        if (agent != null) {
            return createUserDetails(
                    agent.getUsername(),
                    agent.getPassword(),
                    "AGENT"
            );
        }

        Customer customer = customerRepository.findByUsername(username)
                .orElse(null);

        if (customer != null) {
            return createUserDetails(
                    customer.getUsername(),
                    customer.getPassword(),
                    "CUSTOMER"
            );
        }

        throw new UsernameNotFoundException(
                "User not found: " + username
        );
    }

    private UserDetails createUserDetails(
            String username,
            String password,
            String role
    ) {

        return User.builder()
                .username(username)
                .password(password)
                .authorities(
                        new SimpleGrantedAuthority("ROLE_" + role)
                )
                .build();
    }
}