package com.example.Employee.Attendance.And.Work.Log.Management.service.impl;

import com.example.Employee.Attendance.And.Work.Log.Management.dto.request.CreateUserRequest;
import com.example.Employee.Attendance.And.Work.Log.Management.entity.User;
import com.example.Employee.Attendance.And.Work.Log.Management.exception.AccessDeniedException;
import com.example.Employee.Attendance.And.Work.Log.Management.exception.ResourceNotFoundException;
import com.example.Employee.Attendance.And.Work.Log.Management.exception.UsernameNotFoundException;
import com.example.Employee.Attendance.And.Work.Log.Management.mapper.UserMapper;
import com.example.Employee.Attendance.And.Work.Log.Management.repository.UserRepository;
import com.example.Employee.Attendance.And.Work.Log.Management.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(CreateUserRequest request) {

        if ("ADMIN".equals(request.getRole())) {
            throw new AccessDeniedException(
                    "You are not allowed to create ADMIN users");
        }

        User user = userMapper.toEntity(request);

        // Encode password using BCrypt
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        if (request.getManagerId() != null) {
            User manager = userRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.getManagerId()));

            user.setManager(manager);
        }
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, CreateUserRequest request) {

        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with ID " + id + " not found"));

        UserMapper.updateEntity(user, request); // update existing entity

        return userRepository.save(user);
    }

    @Override
    public void disableUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with ID " + id + " not found"));
        user.setEnabled(false);
        userRepository.save(user);
    }
}
