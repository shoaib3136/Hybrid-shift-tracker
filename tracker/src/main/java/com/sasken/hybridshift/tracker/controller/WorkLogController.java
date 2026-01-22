package com.sasken.hybridshift.tracker.controller;

import com.sasken.hybridshift.tracker.entity.AppUser;
import com.sasken.hybridshift.tracker.entity.WorkLog;
import com.sasken.hybridshift.tracker.repository.AppUserRepository;
import com.sasken.hybridshift.tracker.repository.WorkLogRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/worklogs")
public class WorkLogController {

    private final WorkLogRepository workLogRepository;
    private final AppUserRepository appUserRepository;

    public WorkLogController(
            WorkLogRepository workLogRepository,
            AppUserRepository appUserRepository
    ) {
        this.workLogRepository = workLogRepository;
        this.appUserRepository = appUserRepository;
    }

    // 👤 USER → Log today’s work mode
    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public WorkLog logTodayWorkMode(
            @RequestParam String workMode,
            Authentication authentication
    ) {
        String username = authentication.getName();

        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate today = LocalDate.now();

        workLogRepository.findByUserAndWorkDate(user, today)
                .ifPresent(log -> {
                    throw new RuntimeException("Work log already exists for today");
                });

        WorkLog log = new WorkLog(user, today, workMode);
        return workLogRepository.save(log);
    }

    // 👤 USER → View own logs
    @GetMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public List<WorkLog> myLogs(Authentication authentication) {
        String username = authentication.getName();

        AppUser user = appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return workLogRepository.findByUser(user);
    }

    // 🧑‍💼 ADMIN → View all logs (Manager Dashboard)
    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<WorkLog> allLogs() {
        return workLogRepository.findAll();
    }

    // 🧑‍💼 ADMIN → View logs by date
    @GetMapping("/date/{date}")
    @PreAuthorize("hasRole('ADMIN')")
    public List<WorkLog> logsByDate(@PathVariable LocalDate date) {
        return workLogRepository.findByWorkDate(date);
    }

    // 🧑‍💼 ADMIN → Attendance summary
    @GetMapping("/summary")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Object[]> attendanceSummary() {
        return workLogRepository.attendanceSummary();
    }
}
