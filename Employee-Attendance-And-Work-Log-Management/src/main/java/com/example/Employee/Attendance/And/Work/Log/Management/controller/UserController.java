package com.example.Employee.Attendance.And.Work.Log.Management.controller;

import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.CreateUserRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.ApiResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.User;
import com.example.Employee.Attendance.And.Work.Log.Management.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE USER
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<User>> createUser(
            @Valid @RequestBody CreateUserRequest request) {

        User user = userService.createUser(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.CREATED.value(),
                        "Employee created successfully",
                        user
                ));
    }

    //  GET USER BY ID
    @GetMapping("/getUserById/{id}")
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable Long id) {

        User user = userService.getUserById(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Employee fetched successfully",
                        user
                ));
    }

    //  GET ALL USERS
    @GetMapping("/getAllUsers")
    public ResponseEntity<ApiResponse<List<User>>> getAllUsers() {

        List<User> users = userService.getAllUsers();

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Employee fetched successfully",
                        users
                ));
    }

    //  UPDATE USER
    @PutMapping("/updateUser/{id}")
    public ResponseEntity<ApiResponse<User>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody CreateUserRequest request) {

        User updatedUser = userService.updateUser(id, request);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Employee updated successfully",
                        updatedUser
                ));
    }

    //  DISABLE USER (SOFT DELETE)
    @PatchMapping("/{id}/disable")
    public ResponseEntity<ApiResponse<Long>> disableUser(@PathVariable Long id) {

        userService.disableUser(id);

        return ResponseEntity.ok(
                new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "Employee disabled successfully",
                        id
                ));
    }
}
