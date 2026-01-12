package com.example.Employee.Attendance.And.Work.Log.Management.service;

import com.example.Employee.Attendance.And.Work.Log.Management.entity.Attendance;

import java.util.List;

public interface AttendanceService {
    Attendance markCheckIn(Long id);

    Attendance markCheckOut(Long attendanceId);

    Attendance getAttendanceById(Long id);

    List<Attendance> getAllAttendance();
}
