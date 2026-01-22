package com.sasken.hybridshift.tracker.controller;

import com.sasken.hybridshift.tracker.entity.Employee;
import com.sasken.hybridshift.tracker.service.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }



    @PostMapping
    public ResponseEntity<Employee> createEmployee(
            @Valid @RequestBody Employee employee) {

        return ResponseEntity.ok(service.saveEmployee(employee));
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<List<Employee>> getEmployees() {
        return ResponseEntity.ok(service.getAllEmployees());
    }

    // READ BY ID
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        Employee employee = service.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody Employee employee) {

        return ResponseEntity.ok(service.updateEmployee(id, employee));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        service.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }
}
