package com.example.ems.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.example.ems.entity.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalTime;

// Request DTO for marking attendance
public class AttendanceRequest {
    @NotNull
    public Long employeeId;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate attendanceDate;

    @JsonFormat(pattern = "HH:mm")
    public LocalTime checkInTime;

    @JsonFormat(pattern = "HH:mm")
    public LocalTime checkOutTime;

    public AttendanceStatus status;

    @Size(max = 500)
    public String notes;
}