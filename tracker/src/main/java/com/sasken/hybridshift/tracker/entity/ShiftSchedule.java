package com.sasken.hybridshift.tracker.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "shift_schedules",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "work_date"})
        }
)
public class ShiftSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Employee
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name = "planned_work_mode", nullable = false)
    private String plannedWorkMode; // REMOTE / OFFICE / HYBRID

    public ShiftSchedule() {}

    public ShiftSchedule(AppUser user, LocalDate workDate, String plannedWorkMode) {
        this.user = user;
        this.workDate = workDate;
        this.plannedWorkMode = plannedWorkMode;
    }

    // Getters & Setters
    public Long getId() { return id; }

    public AppUser getUser() { return user; }
    public void setUser(AppUser user) { this.user = user; }

    public LocalDate getWorkDate() { return workDate; }
    public void setWorkDate(LocalDate workDate) { this.workDate = workDate; }

    public String getPlannedWorkMode() { return plannedWorkMode; }
    public void setPlannedWorkMode(String plannedWorkMode) {
        this.plannedWorkMode = plannedWorkMode;
    }
}
