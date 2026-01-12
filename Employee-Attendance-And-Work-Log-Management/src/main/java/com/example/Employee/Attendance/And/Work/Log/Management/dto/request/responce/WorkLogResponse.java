package com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce;

import com.example.Employee.Attendance.And.Work.Log.Management.enums.WorkLogStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkLogResponse {
    private Long id;
    private Long userId;
    private LocalDate workDate;
    private String taskSummary;
    private Integer hoursWorked;
    private WorkLogStatus status;
    private String managerRemarks;
}
