package com.sasken.hybridshift.tracker.controller;

import com.sasken.hybridshift.tracker.entity.AppUser;
import com.sasken.hybridshift.tracker.entity.ShiftSchedule;
import com.sasken.hybridshift.tracker.repository.AppUserRepository;
import com.sasken.hybridshift.tracker.repository.ShiftScheduleRepository;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/shifts")
public class ShiftScheduleController {

    private final ShiftScheduleRepository shiftRepo;
    private final AppUserRepository userRepo;

    public ShiftScheduleController(
            ShiftScheduleRepository shiftRepo,
            AppUserRepository userRepo
    ) {
        this.shiftRepo = shiftRepo;
        this.userRepo = userRepo;
    }

    // 🔹 ADMIN → Assign shift to employee
    @PostMapping
    public ShiftSchedule assignShift(
            @RequestParam String username,
            @RequestParam String workMode,
            @RequestParam String date
    ) {
        AppUser user = userRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        LocalDate workDate = LocalDate.parse(date);

        shiftRepo.findByUserAndWorkDate(user, workDate)
                .ifPresent(s -> {
                    throw new RuntimeException("Shift already assigned");
                });

        ShiftSchedule schedule = new ShiftSchedule(
                user,
                workDate,
                workMode
        );

        return shiftRepo.save(schedule);
    }

    // 🔹 USER → View own shift plan
    @GetMapping("/me")
    public List<ShiftSchedule> mySchedule(Authentication auth) {
        AppUser user = userRepo.findByUsername(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        return shiftRepo.findByUser(user);
    }

    // 🔹 ADMIN → View all shift plans
    @GetMapping
    public List<ShiftSchedule> allSchedules() {
        return shiftRepo.findAll();
    }
}
