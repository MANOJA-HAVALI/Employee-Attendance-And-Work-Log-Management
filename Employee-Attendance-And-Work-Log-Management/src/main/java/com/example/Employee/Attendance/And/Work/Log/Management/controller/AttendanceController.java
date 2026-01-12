package com.example.Employee.Attendance.And.Work.Log.Management.controller;

import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.AttendanceRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.ApiResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.Attendance;
import com.example.Employee.Attendance.And.Work.Log.Management.service.AttendanceService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Attendance", description = "Employee check-in and check-out APIs")
@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // CHECK-IN
    @PostMapping("/check-in/{attendanceId}")
    public ResponseEntity<ApiResponse<Attendance>> checkIn(@PathVariable Long attendanceId) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println("User: " + auth.getName());
        System.out.println("Roles: " + auth.getAuthorities());

        String email = auth.getName(); // from JWT

        Attendance attendance = attendanceService.markCheckIn(attendanceId);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value(),
                        "Check-in marked successfully",
                        attendance
                ));
    }


    //  CHECK-OUT
    @PostMapping("/check-out/{attendanceId}")
    public ResponseEntity<ApiResponse<Attendance>> checkOut(
            @PathVariable Long attendanceId) {

        Attendance attendance = attendanceService.markCheckOut(attendanceId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Check-out marked successfully",
                        attendance
                ));
    }

    //  GET ATTENDANCE BY ID
    @GetMapping("/getAttendanceById/{id}")
    public ResponseEntity<ApiResponse<Attendance>> getAttendanceById(
            @PathVariable Long id) {

        Attendance attendance = attendanceService.getAttendanceById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Attendance fetched successfully",
                        attendance
                ));
    }

    //  GET ALL ATTENDANCE
    @GetMapping("/getAllAttendance")
    public ResponseEntity<ApiResponse<List<Attendance>>> getAllAttendance() {

        List<Attendance> attendanceList = attendanceService.getAllAttendance();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "All attendance records fetched successfully",
                        attendanceList
                ));
    }
}
