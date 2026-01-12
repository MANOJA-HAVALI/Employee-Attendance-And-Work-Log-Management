package com.example.Employee.Attendance.And.Work.Log.Management.service;

public interface EmailService {
    void sendWorkLogApprovedMail(String to, String employeeName, String date);
    void sendWorkLogRejectedMail(String to, String employeeName, String date, String remarks);
}
