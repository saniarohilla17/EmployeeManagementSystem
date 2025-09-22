package com.example.ems.dto;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;
// DTO for quick check-in/check-out operations
public class QuickAttendanceRequest {
    @NotNull
    public Long employeeId;

    @NotNull
    public String action; // "checkin" or "checkout"

    @JsonFormat(pattern = "HH:mm")
    public LocalTime time;

    @JsonFormat(pattern = "yyyy-MM-dd")
    public LocalDate date; // Optional, defaults to today
}