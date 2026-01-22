package com.sasken.hybridshift.tracker.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "work_logs",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "work_date"})
        }
)
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 🔐 Logged-in user
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private AppUser user;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    @Column(name = "work_mode", nullable = false)
    private String workMode; // REMOTE / OFFICE / HYBRID

    public WorkLog() {}

    public WorkLog(AppUser user, LocalDate workDate, String workMode) {
        this.user = user;
        this.workDate = workDate;
        this.workMode = workMode;
    }

    public Long getId() {
        return id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }

    public LocalDate getWorkDate() {
        return workDate;
    }

    public void setWorkDate(LocalDate workDate) {
        this.workDate = workDate;
    }

    public String getWorkMode() {
        return workMode;
    }

    public void setWorkMode(String workMode) {
        this.workMode = workMode;
    }
}
