package com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;

@JsonPropertyOrder({ "timestamp", "status", "message", "data" })
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private LocalDateTime timestamp;
    private int status;
    private String message;
    private T data;

    public ApiResponse( String message,T data) {
        this.data = data;
        this.message = message;
    }
}
