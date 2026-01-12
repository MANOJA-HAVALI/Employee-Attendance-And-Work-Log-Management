package com.example.Employee.Attendance.And.Work.Log.Management.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AttendanceRequest {

    @NotNull(message = "User ID is required")
    private Long userId;
}
