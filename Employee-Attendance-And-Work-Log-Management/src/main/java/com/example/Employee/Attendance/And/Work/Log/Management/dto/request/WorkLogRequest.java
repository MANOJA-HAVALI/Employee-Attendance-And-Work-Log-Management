package com.example.Employee.Attendance.And.Work.Log.Management.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class WorkLogRequest {

    @NotBlank(message = "Task summary cannot be blank")
    @Size(max = 500, message = "Task summary cannot exceed 500 characters")
    private String taskSummary;

    @NotNull(message = "Hours worked is required")
    @Min(value = 1, message = "Hours worked must be at least 1 hour")
    private Integer hoursWorked;

    @Size(max = 500, message = "Comments cannot exceed 500 characters")
    private String comments;

}
