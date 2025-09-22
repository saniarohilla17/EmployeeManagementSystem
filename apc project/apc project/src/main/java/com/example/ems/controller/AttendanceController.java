package com.example.ems.controller;

import com.example.ems.dto.AttendanceRequest;
import com.example.ems.dto.AttendanceResponse;
import com.example.ems.dto.QuickAttendanceRequest;
import com.example.ems.service.AttendanceService;
import com.example.ems.summary.AttendanceSummary;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@CrossOrigin
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public ResponseEntity<AttendanceResponse> markAttendance(@Valid @RequestBody AttendanceRequest request) {
        AttendanceResponse response = attendanceService.markAttendance(request);
        return ResponseEntity.created(URI.create("/api/attendance/" + response.getId())).body(response);
    }
    @PostMapping("/quick")
    public ResponseEntity<AttendanceResponse> quickAttendance(@Valid @RequestBody QuickAttendanceRequest request) {
        AttendanceResponse response = attendanceService.quickAttendance(request);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{id}")
    public AttendanceResponse getAttendance(@PathVariable Long id) {
        return attendanceService.findById(id);
    }

    // Get attendance for specific employee and date
    @GetMapping("/employee/{employeeId}/date/{date}")
    public AttendanceResponse getAttendanceByEmployeeAndDate(
            @PathVariable Long employeeId,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return attendanceService.findByEmployeeAndDate(employeeId, date);
    }

    // Get all attendance records for a specific date
    @GetMapping("/date/{date}")
    public List<AttendanceResponse> getAttendanceByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return attendanceService.findByDate(date);
    }

    // Get today's attendance records
    @GetMapping("/today")
    public List<AttendanceResponse> getTodayAttendance() {
        return attendanceService.findByDate(LocalDate.now());
    }

    // Get all attendance records for a specific employee
    @GetMapping("/employee/{employeeId}")
    public List<AttendanceResponse> getAttendanceByEmployee(@PathVariable Long employeeId) {
        return attendanceService.findByEmployee(employeeId);
    }

    // Get attendance records within a date range
    @GetMapping
    public List<AttendanceResponse> getAttendanceByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) Long employeeId) {

        if (employeeId != null) {
            return attendanceService.findByEmployeeAndDateRange(employeeId, startDate, endDate);
        } else {
            return attendanceService.findByDateRange(startDate, endDate);
        }
    }

    // Get attendance summary for an employee within a date range
    @GetMapping("/employee/{employeeId}/summary")
    public AttendanceSummary getAttendanceSummary(
            @PathVariable Long employeeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return attendanceService.getAttendanceSummary(employeeId, startDate, endDate);
    }

    // Update attendance record
    @PutMapping("/{id}")
    public AttendanceResponse updateAttendance(@PathVariable Long id, @Valid @RequestBody AttendanceRequest request) {
        return attendanceService.updateAttendance(id, request);
    }

    // Delete attendance record
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttendance(@PathVariable Long id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.noContent().build();
    }

    // Bulk operations endpoints

    // Get this month's attendance for all employees
    @GetMapping("/monthly")
    public List<AttendanceResponse> getMonthlyAttendance(
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer month) {

        LocalDate now = LocalDate.now();
        int targetYear = year != null ? year : now.getYear();
        int targetMonth = month != null ? month : now.getMonthValue();

        LocalDate startDate = LocalDate.of(targetYear, targetMonth, 1);
        LocalDate endDate = startDate.plusMonths(1).minusDays(1);

        return attendanceService.findByDateRange(startDate, endDate);
    }

    // Get this week's attendance for all employees
    @GetMapping("/weekly")
    public List<AttendanceResponse> getWeeklyAttendance() {
        LocalDate now = LocalDate.now();
        LocalDate startOfWeek = now.minusDays(now.getDayOfWeek().getValue() - 1);
        LocalDate endOfWeek = startOfWeek.plusDays(6);

        return attendanceService.findByDateRange(startOfWeek, endOfWeek);
    }
}