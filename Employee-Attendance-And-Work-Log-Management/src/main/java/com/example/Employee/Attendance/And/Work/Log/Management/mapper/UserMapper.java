package com.example.Employee.Attendance.And.Work.Log.Management.mapper;

import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.CreateUserRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.UserResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.User;
import com.example.Employee.Attendance.And.Work.Log.Management.enums.Role;
import org.springframework.stereotype.Component;


//If your mapper methods are static, remove UserMapper from the constructor and call static methods.
//If your mapper methods are instance methods, annotate UserMapper with @Component so Spring can inject it.

@Component
public class UserMapper {


    // CREATE
    public User toEntity(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        if(request.getRole() != null){
            user.setRole(Role.valueOf(request.getRole()));
        }

        return user;
    }

    // UPDATE
    public static void updateEntity(User user, CreateUserRequest request) {
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        if(request.getRole() != null){
            user.setRole(Role.valueOf(request.getRole()));
        }
    }

    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().getDeclaringClass().getTypeName());
        response.setEnabled(user.isEnabled());
        return response;
    }
}
