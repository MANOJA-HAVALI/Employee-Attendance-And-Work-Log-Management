package com.example.Employee.Attendance.And.Work.Log.Management.exception;

public class DuplicateRecordException extends RuntimeException{
    public DuplicateRecordException(String message) {
        super(message);
    }
}
