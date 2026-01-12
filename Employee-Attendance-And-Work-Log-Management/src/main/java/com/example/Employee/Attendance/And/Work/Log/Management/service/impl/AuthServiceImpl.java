package com.example.Employee.Attendance.And.Work.Log.Management.service.impl;


import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.LoginRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.LoginResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.UserResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.User;
import com.example.Employee.Attendance.And.Work.Log.Management.mapper.UserMapper;
import com.example.Employee.Attendance.And.Work.Log.Management.repository.UserRepository;
import com.example.Employee.Attendance.And.Work.Log.Management.service.AuthService;
import com.example.Employee.Attendance.And.Work.Log.Management.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserMapper userMapper ;
    @Autowired
    private final AuthenticationManager authenticationManager;


    public AuthServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, UserMapper userMapper, AuthenticationManager authenticationManager) {
        this.userMapper = userMapper;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name(),true);
        UserResponse userResponse = userMapper.toResponse(user);
        return new LoginResponse(token, userResponse);
    }



//
//    @Override
//    public String register(CreateUserRequest request) {
//        User user = new User();
//        user.setName(request.getName());
//        user.setEmail(request.getEmail());
//        user.setPassword(passwordEncoder.encode(request.getPassword()));
//
//        //  Convert String → Enum safely
//        try {
//            user.setRole(Role.valueOf(request.getRole().toUpperCase()));
//        } catch (IllegalArgumentException ex) {
//            throw new IllegalArgumentException(
//                    "Invalid role. Allowed values: ADMIN, MANAGER, EMPLOYEE");
//        }
//
//        //  Manager mapping (only for EMPLOYEE)
//        if ("EMPLOYEE".equalsIgnoreCase(request.getRole())) {
//
//            if (request.getManagerId() == null) {
//                throw new IllegalArgumentException("managerId is required for EMPLOYEE role");
//            }
//
//            User manager = userRepository.findById(request.getManagerId())
//                    .orElseThrow(() ->
//                            new ResourceNotFoundException("Manager not found"));
//
//            user.setManager(manager);
//        }
//        log.info("User registered successfully with email: {}", request.getEmail());
//
//        user.setEnabled(true);
//
//        userRepository.save(user);
//        return "User registered successfully";
//    }
}
