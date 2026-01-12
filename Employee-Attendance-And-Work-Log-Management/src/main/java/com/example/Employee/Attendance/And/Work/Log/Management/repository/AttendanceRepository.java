package com.example.Employee.Attendance.And.Work.Log.Management.repository;

import com.example.Employee.Attendance.And.Work.Log.Management.entity.Attendance;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance , Long> {

    Optional<Attendance> findByUser_IdAndAttendanceDate(Long userId, LocalDate attendanceDate);
}

