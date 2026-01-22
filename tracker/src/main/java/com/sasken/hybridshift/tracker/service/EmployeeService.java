package com.sasken.hybridshift.tracker.service;

import com.sasken.hybridshift.tracker.entity.Employee;
import com.sasken.hybridshift.tracker.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public Employee saveEmployee(Employee employee) {
        return repository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }
    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        Employee existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        existing.setName(updatedEmployee.getName());
        existing.setEmail(updatedEmployee.getEmail());
        existing.setWorkMode(updatedEmployee.getWorkMode());
        existing.setWorkDate(updatedEmployee.getWorkDate());

        return repository.save(existing);
    }
    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }

    public Employee getEmployeeById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found with id " + id));
    }


}
