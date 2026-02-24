package com.example.Employee.Attendance.And.Work.Log.Management.controller;

import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.LoginRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.ApiResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.LoginResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@Tag(name = "Authentication", description = "Login and registration APIs")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    //  LOGIN
    @Operation(summary = "User Login", description = "user have to give correct credentials. ")
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {

        LoginResponse loginResponse = authService.login(request);

        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(
                        LocalDateTime.now(),
                        HttpStatus.OK.value(),
                        "User login successfully",
                        loginResponse
                ));
    }

//    // REGISTER
//    @PostMapping("/register")
//    public ResponseEntity<ApiResponse<String>> register(@Valid @RequestBody CreateUserRequest request) {
//
//        logger.info("Registering user with email: {}", request.getEmail());
//
//        String savedUser = authService.register(request);
//
//        logger.info("User registered successfully with email: {}", request.getName());
//
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .body(new ApiResponse<>(
//                        LocalDateTime.now(),
//                        HttpStatus.CREATED.value(),
//                        "User register successfully",
//                        savedUser
//                ));
//    }
}
