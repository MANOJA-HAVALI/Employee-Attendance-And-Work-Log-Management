package com.example.Employee.Attendance.And.Work.Log.Management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // Public endpoints
                        .requestMatchers("/api/auth/**", "/health",
                                "/swagger-ui/**", "/v3/api-docs/**").permitAll()

                        //Admin endpoints
                        .requestMatchers("/api/admin/**"
                                                ,"/api/attendance/getAttendanceById/**"
                                                ,"/api/attendance/getAllAttendance/"
                                                ,"/api/worklogs/**").hasRole("ADMIN")

                        // Manager endpoints
                        .requestMatchers("/api/attendance/**"
                                                ,"/api/worklogs/**").hasRole("MANAGER")

                        // Employee endpoints
                        .requestMatchers("/api/attendance/check-in/**"
                                                ,"/api/attendance/check-out/**"
                                                ,"/api/attendance/getAttendanceById/**"
                                                ,"/api/worklogs/submit"
                                                ,"/api/worklogs/my").hasRole("EMPLOYEE")
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}