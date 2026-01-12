package com.example.Employee.Attendance.And.Work.Log.Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class EmployeeAttendanceAndWorkLogManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeAttendanceAndWorkLogManagementApplication.class, args);
	}

}
