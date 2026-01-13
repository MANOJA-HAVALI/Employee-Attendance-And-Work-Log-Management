package com.example.Employee.Attendance.And.Work.Log.Management.controller;

import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.WorkLogRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.ApiResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.WorkLogResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.service.WorkLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Work Logs", description = "Daily work log submission and approval APIs")
@RestController
@RequestMapping("/api/worklogs")
public class WorkLogController {

    private final WorkLogService workLogService;
    public WorkLogController(WorkLogService workLogService) {
        this.workLogService = workLogService;
    }

    // ================= EMPLOYEE APIs =================

    // Submit work log (Employee)
    @PostMapping("/submit/{userId}")
    public ResponseEntity<ApiResponse<WorkLogResponse>> submitWorkLog(
            @PathVariable Long userId,
            @Valid @RequestBody WorkLogRequest request) {

        WorkLogResponse response = workLogService.submitWorkLog(userId, request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value(),
                        "Work log submitted successfully",
                        response
                ));
    }

    //  Get my work logs (Employee)
    @GetMapping("/my/{userId}")
    public ResponseEntity<ApiResponse<List<WorkLogResponse>>> getMyWorkLogs(
            @PathVariable Long userId) {

        List<WorkLogResponse> workLogs = workLogService.getMyWorkLogs(userId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Work logs fetched successfully",
                        workLogs
                ));
    }

    // ================= MANAGER APIs =================

    // Approve work log (Manager)
    @PostMapping("/approve/{workLogId}")
    public ResponseEntity<ApiResponse<Void>> approveWorkLog(
            @PathVariable Long workLogId,
            @RequestParam(required = false) String remarks,
            @RequestParam Long managerId) {

        workLogService.approveWorkLog(workLogId, remarks, managerId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Work log approved successfully",
                        null
                ));
    }

    // Reject work log (Manager)
    @PostMapping("/reject/{workLogId}")
    public ResponseEntity<ApiResponse<Void>> rejectWorkLog(
            @PathVariable Long workLogId,
            @RequestParam String remarks,
            @RequestParam Long managerId) {

        workLogService.rejectWorkLog(workLogId, remarks, managerId);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Work log rejected successfully",
                        null
                ));
    }
}
