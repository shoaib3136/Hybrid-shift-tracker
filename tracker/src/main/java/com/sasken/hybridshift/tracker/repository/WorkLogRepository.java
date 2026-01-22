package com.sasken.hybridshift.tracker.repository;

import com.sasken.hybridshift.tracker.entity.WorkLog;
import com.sasken.hybridshift.tracker.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {

    // ❌ Prevent duplicate log
    Optional<WorkLog> findByUserAndWorkDate(AppUser user, LocalDate workDate);

    // 👤 USER logs
    List<WorkLog> findByUser(AppUser user);

    // 📅 ADMIN → logs by date
    List<WorkLog> findByWorkDate(LocalDate workDate);

    // 📊 ADMIN → attendance summary
    @Query("""
        SELECT w.workMode, COUNT(w)
        FROM WorkLog w
        GROUP BY w.workMode
    """)
    List<Object[]> attendanceSummary();
}
