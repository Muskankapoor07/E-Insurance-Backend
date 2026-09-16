package com.einsurance.repository;

import com.einsurance.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    Optional<Employee> findByUsername(String username);

    Optional<Employee> findByEmail(String email);
}