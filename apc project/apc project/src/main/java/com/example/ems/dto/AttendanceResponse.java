package com.example.ems.dto;
import com.example.ems.entity.Attendance;

import com.example.ems.entity.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;


// Response DTO for attendance data
public class AttendanceResponse {
    private Long id;
    private Long employeeId;
    private String employeeName;
    private LocalDate attendanceDate;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private AttendanceStatus status;
    private Double totalHours;
    private String notes;

    public AttendanceResponse(Attendance attendance) {
        this.id = attendance.getId();
        this.employeeId = attendance.getEmployee().getId();
        this.employeeName = attendance.getEmployee().getName();
        this.attendanceDate = attendance.getAttendanceDate();
        this.checkInTime = attendance.getCheckInTime();
        this.checkOutTime = attendance.getCheckOutTime();
        this.status = attendance.getStatus();
        this.totalHours = attendance.getTotalHours();
        this.notes = attendance.getNotes();
    }

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public LocalDate getAttendanceDate() { return attendanceDate; }
    public void setAttendanceDate(LocalDate attendanceDate) { this.attendanceDate = attendanceDate; }

    public LocalTime getCheckInTime() { return checkInTime; }
    public void setCheckInTime(LocalTime checkInTime) { this.checkInTime = checkInTime; }

    public LocalTime getCheckOutTime() { return checkOutTime; }
    public void setCheckOutTime(LocalTime checkOutTime) { this.checkOutTime = checkOutTime; }

    public AttendanceStatus getStatus() { return status; }
    public void setStatus(AttendanceStatus status) { this.status = status; }

    public Double getTotalHours() { return totalHours; }
    public void setTotalHours(Double totalHours) { this.totalHours = totalHours; }

    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}