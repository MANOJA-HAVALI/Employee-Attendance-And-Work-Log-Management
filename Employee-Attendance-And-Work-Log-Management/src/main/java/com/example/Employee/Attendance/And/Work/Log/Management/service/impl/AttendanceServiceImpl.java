package com.example.Employee.Attendance.And.Work.Log.Management.service.impl;

import com.example.Employee.Attendance.And.Work.Log.Management.entity.Attendance;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.User;
import com.example.Employee.Attendance.And.Work.Log.Management.repository.AttendanceRepository;
import com.example.Employee.Attendance.And.Work.Log.Management.repository.UserRepository;
import com.example.Employee.Attendance.And.Work.Log.Management.service.AttendanceService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;

    public AttendanceServiceImpl(AttendanceRepository attendanceRepository, UserRepository userRepository) {
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
    }
    @Override
    public Attendance markCheckIn(Long id) {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String email = authentication.getName(); // username

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found"));

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setCheckInTime(LocalDateTime.now());
        attendance.setAttendanceDate(LocalDate.now());

        return attendanceRepository.save(attendance);
    }

    @Override
    public Attendance markCheckOut(Long attendanceId) {

            Attendance attendance = attendanceRepository.findById(attendanceId)
                    .orElseThrow(() ->
                            new RuntimeException("Attendance not found with ID " + attendanceId));

            if (attendance.getCheckOutTime() != null) {
                throw new RuntimeException("User already checked out");
            }

            if (attendance.getCheckInTime() == null) {
                throw new RuntimeException("User has not checked in");
            }

            LocalDateTime checkOutTime = LocalDateTime.now();
            attendance.setCheckOutTime(checkOutTime);

            // Calculate total working hours
            Duration duration = Duration.between(
                    attendance.getCheckInTime(),
                    checkOutTime
            );

            double totalHours = duration.toMinutes() / 60.0; // hours with decimals
            attendance.setTotalHours((long) totalHours);

            return attendanceRepository.save(attendance);
        }

    @Override
    public Attendance getAttendanceById(Long id) {
            return attendanceRepository.findById(id)
                    .orElseThrow(() ->
                        new RuntimeException("Attendance not found with ID " + id));
    }

    @Override
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

}