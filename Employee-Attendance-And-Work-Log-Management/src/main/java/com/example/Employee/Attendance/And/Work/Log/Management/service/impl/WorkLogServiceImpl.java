package com.example.Employee.Attendance.And.Work.Log.Management.service.impl;

import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.WorkLogRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.WorkLogResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.Attendance;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.User;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.WorkLog;
import com.example.Employee.Attendance.And.Work.Log.Management.enums.WorkLogStatus;
import com.example.Employee.Attendance.And.Work.Log.Management.exception.DuplicateRecordException;
import com.example.Employee.Attendance.And.Work.Log.Management.exception.InvalidAttendanceException;
import com.example.Employee.Attendance.And.Work.Log.Management.exception.ResourceNotFoundException;
import com.example.Employee.Attendance.And.Work.Log.Management.exception.UnauthorizedAccessException;
import com.example.Employee.Attendance.And.Work.Log.Management.mapper.WorkLogMapper;
import com.example.Employee.Attendance.And.Work.Log.Management.repository.AttendanceRepository;
import com.example.Employee.Attendance.And.Work.Log.Management.repository.UserRepository;
import com.example.Employee.Attendance.And.Work.Log.Management.repository.WorkLogRepository;
import com.example.Employee.Attendance.And.Work.Log.Management.service.EmailService;
import com.example.Employee.Attendance.And.Work.Log.Management.service.WorkLogService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WorkLogServiceImpl implements WorkLogService {

    private final WorkLogRepository workLogRepository;
    private final AttendanceRepository attendanceRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    public WorkLogServiceImpl(WorkLogRepository workLogRepository, AttendanceRepository attendanceRepository, UserRepository userRepository, EmailService emailService) {
        this.workLogRepository = workLogRepository;
        this.attendanceRepository = attendanceRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    // ================= EMPLOYEE =================
    @Override
    public WorkLogResponse submitWorkLog(Long userId, WorkLogRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        LocalDate today = LocalDate.now();

        // One work log per day validation
        if (workLogRepository.findByUser_IdAndWorkDate(userId, today).isPresent()) {
            throw new DuplicateRecordException("Work log already submitted for today");
        }

        // Attendance must exist
        Attendance attendance = attendanceRepository
                .findByUser_IdAndAttendanceDate(userId, today)
                .orElseThrow(() ->
                        new InvalidAttendanceException("Attendance not found for today"));

        // Use Mapper instead of manual entity creation
        WorkLog workLog = WorkLogMapper.toEntity(request, user, attendance, today);

        WorkLog savedLog = workLogRepository.save(workLog);
        return WorkLogMapper.toResponse(savedLog);

    }

    @Override
    public List<WorkLogResponse> getMyWorkLogs(Long userId) {
        return workLogRepository.findByUser_Id(userId)
                .stream()
                .map(WorkLogMapper::toResponse)
                .collect(Collectors.toList());

    }

    // ================= MANAGER =================
    @Override
    public void approveWorkLog(Long workLogId, String remarks, Long managerId) {
//        WorkLog workLog = getWorkLogForManager(workLogId, managerId);
//
//        workLog.setStatus(WorkLogStatus.APPROVED);
//        workLog.setManagerRemarks(remarks);
//
//        workLogRepository.save(workLog);
        WorkLog workLog = workLogRepository.findById(workLogId)
                .orElseThrow(()-> new ResourceNotFoundException("Work log not found"));

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));

        workLog.setStatus(WorkLogStatus.APPROVED);
        workLog.setManagerRemarks(remarks);

        workLogRepository.save(workLog);

        // 📧 Send email
        User employee = workLog.getUser();
        emailService.sendWorkLogApprovedMail(
                employee.getEmail(),
                //"manoj@yopmail.com",
                employee.getName(),
                workLog.getWorkDate().toString()
        );
    }

    @Override
    public void rejectWorkLog(Long workLogId, String remarks, Long managerId) {
        if (remarks == null || remarks.isBlank()) {
            throw new InvalidAttendanceException("Remarks are mandatory for rejection");
        }
        WorkLog workLog = workLogRepository.findById(workLogId)
                .orElseThrow(() -> new ResourceNotFoundException("Work log not found"));

        User manager = userRepository.findById(managerId)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));

//        WorkLog workLog = getWorkLogForManager(workLogId, managerId);
//        workLog.setStatus(WorkLogStatus.REJECTED);
//        workLog.setManagerRemarks(remarks);
//        workLogRepository.save(workLog);
        workLog.setStatus(WorkLogStatus.REJECTED);
        workLog.setManagerRemarks(remarks);

        workLogRepository.save(workLog);

        // 📧 Send email
        User employee = workLog.getUser();
        emailService.sendWorkLogRejectedMail(
                employee.getEmail(),
                employee.getName(),
                workLog.getWorkDate().toString(),
                remarks
        );
    }

    // ================= COMMON =================
    @Override
    public WorkLog getWorkLogForManager(Long workLogId, Long managerId) {

        WorkLog workLog = workLogRepository.findById(workLogId)
                .orElseThrow(() -> new ResourceNotFoundException("Work log not found"));

        User employee = workLog.getUser();

        if (employee.getManager() == null ||
                !employee.getManager().getId().equals(managerId)) {
            throw new UnauthorizedAccessException(
                    "You are not authorized to review this work log");
        }

        return workLog;
    }
}
