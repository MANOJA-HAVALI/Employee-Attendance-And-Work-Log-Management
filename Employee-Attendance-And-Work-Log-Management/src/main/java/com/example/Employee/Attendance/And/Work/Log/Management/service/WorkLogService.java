package com.example.Employee.Attendance.And.Work.Log.Management.service;

import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.WorkLogRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.WorkLogResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.WorkLog;

import java.util.List;

public interface WorkLogService {
    // Employee submits daily work log
    WorkLogResponse submitWorkLog(Long userId, WorkLogRequest request);

    // Employee views own work log history
    List<WorkLogResponse> getMyWorkLogs(Long userId);

    // Manager approves work log
    void approveWorkLog(Long workLogId, String remarks, Long managerId);

    // Manager rejects work log
    void rejectWorkLog(Long workLogId, String remarks, Long managerId);

    WorkLog getWorkLogForManager(Long workLogId, Long managerId);
}
