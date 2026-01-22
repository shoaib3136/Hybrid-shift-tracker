package com.sasken.hybridshift.tracker.repository;

import com.sasken.hybridshift.tracker.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
}
