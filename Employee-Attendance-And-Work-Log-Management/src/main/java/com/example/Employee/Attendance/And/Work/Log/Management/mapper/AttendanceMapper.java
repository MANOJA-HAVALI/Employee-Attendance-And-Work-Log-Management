package com.example.Employee.Attendance.And.Work.Log.Management.mapper;

import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.AttendanceResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.Attendance;
import org.springframework.stereotype.Component;

@Component
public class AttendanceMapper {

    public static AttendanceResponse toResponse(Attendance attendance) {
        AttendanceResponse response = new AttendanceResponse();
        response.setId(attendance.getId());
        response.setUserId(attendance.getUser().getId());
        response.setDate(attendance.getAttendanceDate());
        response.setCheckInTime(attendance.getCheckInTime());
        response.setCheckOutTime(attendance.getCheckOutTime());
        response.setTotalWorkingMinutes(attendance.getTotalHours());
        return response;
}
}
