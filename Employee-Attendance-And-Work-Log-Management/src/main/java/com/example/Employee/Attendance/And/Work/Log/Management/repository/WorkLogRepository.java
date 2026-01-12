package com.example.Employee.Attendance.And.Work.Log.Management.repository;

import com.example.Employee.Attendance.And.Work.Log.Management.entity.User;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.WorkLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface WorkLogRepository extends JpaRepository<WorkLog, Long> {
    Optional<User> findByUserAndWorkDate(User user, String workDate);
    Optional<WorkLog> findByUser_IdAndWorkDate(Long userId, LocalDate workDate);
    List<WorkLog> findByUser_Id(Long userId);
}

