package com.sasken.hybridshift.tracker.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Work mode is required")
    private String workMode;

    @NotNull(message = "Work date is required")
    private LocalDate workDate;

    public Employee() {}

    // ✅ GETTERS
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getWorkMode() {
        return workMode;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    // ✅ SETTERS
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }
}
