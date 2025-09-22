package com.example.ems.summary;

import java.time.LocalDate;

// DTO for attendance summary/statistics
public class AttendanceSummary {
    private Long employeeId;
    private String employeeName;
    private LocalDate startDate;
    private LocalDate endDate;
    private long totalDays;
    private long presentDays;
    private long absentDays;
    private long lateDays;
    private long halfDays;
    private long leaveDays;
    private double averageHours;
    private double totalHours;

    // Constructors
    public AttendanceSummary() {}

    public AttendanceSummary(Long employeeId, String employeeName, LocalDate startDate, LocalDate endDate) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters and setters
    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public long getTotalDays() { return totalDays; }
    public void setTotalDays(long totalDays) { this.totalDays = totalDays; }

    public long getPresentDays() { return presentDays; }
    public void setPresentDays(long presentDays) { this.presentDays = presentDays; }

    public long getAbsentDays() { return absentDays; }
    public void setAbsentDays(long absentDays) { this.absentDays = absentDays; }

    public long getLateDays() { return lateDays; }
    public void setLateDays(long lateDays) { this.lateDays = lateDays; }

    public long getHalfDays() { return halfDays; }
    public void setHalfDays(long halfDays) { this.halfDays = halfDays; }

    public long getLeaveDays() { return leaveDays; }
    public void setLeaveDays(long leaveDays) { this.leaveDays = leaveDays; }

    public double getAverageHours() { return averageHours; }
    public void setAverageHours(double averageHours) { this.averageHours = averageHours; }

    public double getTotalHours() { return totalHours; }
    public void setTotalHours(double totalHours) { this.totalHours = totalHours; }
}