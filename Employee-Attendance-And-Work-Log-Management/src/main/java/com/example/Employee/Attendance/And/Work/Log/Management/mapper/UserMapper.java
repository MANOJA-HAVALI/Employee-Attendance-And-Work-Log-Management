package com.example.Employee.Attendance.And.Work.Log.Management.mapper;

import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.CreateUserRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.responce.UserResponse;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.User;
import com.example.Employee.Attendance.And.Work.Log.Management.enums.Role;
import com.example.Employee.Attendance.And.Work.Log.Management.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


//If your mapper methods are static, remove UserMapper from the constructor and call static methods.
//If your mapper methods are instance methods, annotate UserMapper with @Component so Spring can inject it.

@Component
public class UserMapper {

    private static UserRepository userRepository = null;
    private static PasswordEncoder passwordEncoder;

    public UserMapper(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ======================== CREATE =======================
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

    // ====================== UPDATE =========================
    public static void updateEntity(User user, CreateUserRequest request) {
        //user.setName(request.getName());
        if (request.getName() != null && !request.getName().equals(user.getName())) {
            user.setName(request.getName());
        }

        //user.setEmail(request.getEmail());
        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            user.setEmail(request.getEmail());
        }
        //user.setPassword(request.getPassword());
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            // encode password if it changed
            // assume you have passwordEncoder bean
            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                user.setPassword(passwordEncoder.encode(request.getPassword()));
            }
        }
        if(request.getRole() != null){
           user.setRole(Role.valueOf(request.getRole()));
        }
        if (request.getManagerId() != null) {
            // check if manager changed
            if (user.getManager() == null || !user.getManager().getId().equals(request.getManagerId())) {
                // fetch manager from DB
                User manager = userRepository.findById(request.getManagerId())
                        .orElseThrow(() -> new RuntimeException("Manager not found"));
                user.setManager(manager);
            }
        }
    }

    public UserResponse toResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole().name());
        response.setEnabled(user.isEnabled());
        return response;
    }
}
