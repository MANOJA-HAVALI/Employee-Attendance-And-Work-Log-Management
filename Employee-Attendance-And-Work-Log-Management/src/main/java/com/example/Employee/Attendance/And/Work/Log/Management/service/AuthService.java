package com.example.Employee.Attendance.And.Work.Log.Management.service;

import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.CreateUserRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.LoginRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.LoginResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);
   // String register(CreateUserRequest request);
}
