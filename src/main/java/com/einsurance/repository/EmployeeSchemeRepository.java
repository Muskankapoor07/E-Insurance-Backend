package com.einsurance.repository;

import com.einsurance.entity.EmployeeScheme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeSchemeRepository extends JpaRepository<EmployeeScheme, Integer> {
    List<EmployeeScheme> findByEmployee_EmployeeId(Integer employeeId);
    List<EmployeeScheme> findByScheme_SchemeId(Integer schemeId);
    boolean existsByEmployee_EmployeeIdAndScheme_SchemeId(Integer employeeId, Integer schemeId);
    void deleteByEmployee_EmployeeIdAndScheme_SchemeId(Integer employeeId, Integer schemeId);
}
