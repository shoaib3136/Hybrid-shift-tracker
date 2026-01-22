package com.sasken.hybridshift.tracker.repository;

import com.sasken.hybridshift.tracker.entity.AppUser;
import com.sasken.hybridshift.tracker.entity.ShiftSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ShiftScheduleRepository
        extends JpaRepository<ShiftSchedule, Long> {

    Optional<ShiftSchedule> findByUserAndWorkDate(
            AppUser user,
            LocalDate workDate
    );

    List<ShiftSchedule> findByUser(AppUser user);
}
