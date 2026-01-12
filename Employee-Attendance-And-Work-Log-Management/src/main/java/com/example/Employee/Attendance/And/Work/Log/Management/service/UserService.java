package com.example.Employee.Attendance.And.Work.Log.Management.service;


import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.CreateUserRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.User;

import java.util.List;

public interface UserService {

    User createUser(CreateUserRequest request);

    User getUserById(Long id);

    List<User> getAllUsers();

    User updateUser(Long id, CreateUserRequest request);

    void disableUser(Long id);

}
