package com.example.Employee.Attendance.And.Work.Log.Management.mapper;

import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.WorkLogRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.WorkLogResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.Attendance;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.User;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.WorkLog;
import com.example.Employee.Attendance.And.Work.Log.Management.enums.WorkLogStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class WorkLogMapper {

    // ======= DTO → Entity =======
    public static WorkLog toEntity(
            WorkLogRequest request,
            User user,
            Attendance attendance,
            LocalDate workDate
    ) {
        WorkLog workLog = new WorkLog();
        workLog.setUser(user);
        workLog.setAttendance(attendance);
        workLog.setWorkDate(workDate);
        workLog.setTaskSummary(request.getTaskSummary());
        workLog.setHoursWorked(request.getHoursWorked());
        workLog.setComments(request.getComments());
        workLog.setStatus(WorkLogStatus.PENDING);
        return workLog;
    }

    public static WorkLogResponse toResponse(WorkLog workLog) {
        WorkLogResponse response = new WorkLogResponse();
        response.setId(workLog.getId());
        response.setUserId(workLog.getUser().getId());
        response.setWorkDate(workLog.getWorkDate());
        response.setTaskSummary(workLog.getTaskSummary());
        response.setHoursWorked(workLog.getHoursWorked());
        response.setStatus(workLog.getStatus());
        response.setManagerRemarks(workLog.getManagerRemarks());
        return response;
    }
}
