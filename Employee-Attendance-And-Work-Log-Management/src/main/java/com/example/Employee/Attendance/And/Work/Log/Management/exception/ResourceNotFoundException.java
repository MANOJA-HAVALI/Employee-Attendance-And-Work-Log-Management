package com.example.Employee.Attendance.And.Work.Log.Management.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String user, String id, Long managerId) {
        super(String.format("%s not found with %s : '%s'", user, id, managerId));
    }

    public ResourceNotFoundException(String user, String username, String username1) {
        super(String.format("%s not found with %s : '%s'", user, username, username1));
    }
}

