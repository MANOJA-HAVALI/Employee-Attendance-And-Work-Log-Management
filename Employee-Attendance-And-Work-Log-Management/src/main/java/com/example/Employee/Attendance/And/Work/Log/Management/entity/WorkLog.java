package com.example.Employee.Attendance.And.Work.Log.Management.entity;

import com.example.Employee.Attendance.And.Work.Log.Management.enums.WorkLogStatus;
import com.example.Employee.Attendance.And.Work.Log.Management.util.AuditModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(
        name = "work_logs",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"user_id", "work_date"})
        }
)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class   WorkLog extends AuditModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "attendance_id", nullable = false)
    private Attendance attendance;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    private String taskSummary;
    private Integer hoursWorked;
    private String comments;

    @Enumerated(EnumType.STRING)
    private WorkLogStatus status;

    private String managerRemarks;
}
