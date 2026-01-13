package com.example.Employee.Attendance.And.Work.Log.Management.service.impl;

import com.example.Employee.Attendance.And.Work.Log.Management.service.EmailService;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

    private MailSender mailSender;

    public EmailServiceImpl(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendWorkLogApprovedMail(String to, String employeeName, String date) {
        SimpleMailMessage mail = new SimpleMailMessage();
        //mail.setTo("manojyop@gmail.com");
        mail.setTo(to);
        mail.setSubject("Work Log Approved ");
        mail.setText(
                "Hello " + employeeName + ",\n\n" +
                        "Your work log for " + date + " has been APPROVED by your manager.\n\n" +
                        "Regards,\nEmployee Management System"
        );

        mailSender.send(mail);
    }

    @Override
    public void sendWorkLogRejectedMail(String to, String employeeName, String date, String remarks) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(to);
        mail.setSubject("Work Log Rejected ");
        mail.setText(
                "Hello " + employeeName + ",\n\n" +
                        "Your work log for " + date + " has been REJECTED.\n" +
                        "Remarks: " + remarks + "\n\n" +
                        "Regards,\nEmployee Management System"
        );

        mailSender.send(mail);
    }
}
