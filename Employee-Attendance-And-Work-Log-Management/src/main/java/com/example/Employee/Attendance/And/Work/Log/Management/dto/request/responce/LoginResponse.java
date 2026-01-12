package com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

    private String token;
    private UserResponse user;

}
