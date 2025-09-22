package com.example.ems.service;

import com.example.ems.summary.AttendanceSummary;
import com.example.ems.dto.AttendanceRequest;
import com.example.ems.dto.AttendanceResponse;
import com.example.ems.dto.QuickAttendanceRequest;
import com.example.ems.entity.Attendance;
import com.example.ems.repository.AttendanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.ems.repository.EmployeeRepository;
import com.example.ems.entity.AttendanceStatus;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.ems.entity.Employee;
import com.example.ems.exception.BadRequestException;
import com.example.ems.exception.ResourceNotFoundException;

@Service
@Transactional
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    // Mark attendance (create or update)
    public AttendanceResponse markAttendance(AttendanceRequest request) {
        Employee employee = employeeRepository.findById(request.employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + request.employeeId));

        // Check if attendance already exists for this employee and date
        Optional<Attendance> existingAttendance = attendanceRepository
                .findByEmployeeIdAndAttendanceDate(request.employeeId, request.attendanceDate);

        Attendance attendance;
        if (existingAttendance.isPresent()) {
            attendance = existingAttendance.get();
        } else {
            attendance = new Attendance(employee, request.attendanceDate);
        }

        // Update attendance details
        if (request.checkInTime != null) {
            attendance.setCheckInTime(request.checkInTime);
        }
        if (request.checkOutTime != null) {
            attendance.setCheckOutTime(request.checkOutTime);
        }
        if (request.status != null) {
            attendance.setStatus(request.status);
        } else {
            // Auto-determine status based on check-in time
            if (request.checkInTime != null) {
                attendance.setStatus(determineStatusFromCheckIn(request.checkInTime));
            }
        }
        if (request.notes != null) {
            attendance.setNotes(request.notes);
        }

        attendance = attendanceRepository.save(attendance);
        return new AttendanceResponse(attendance);
    }

    // Quick check-in or check-out
    public AttendanceResponse quickAttendance(QuickAttendanceRequest request) {
        Employee employee = employeeRepository.findById(request.employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + request.employeeId));

        LocalDate date = request.date != null ? request.date : LocalDate.now();
        LocalTime time = request.time != null ? request.time : LocalTime.now();

        Optional<Attendance> existingAttendance = attendanceRepository
                .findByEmployeeIdAndAttendanceDate(request.employeeId, date);

        Attendance attendance;
        if (existingAttendance.isPresent()) {
            attendance = existingAttendance.get();
        } else {
            attendance = new Attendance(employee, date);
        }

        if ("checkin".equalsIgnoreCase(request.action)) {
            if (attendance.getCheckInTime() != null) {
                throw new BadRequestException("Employee has already checked in today");
            }
            attendance.setCheckInTime(time);
            attendance.setStatus(determineStatusFromCheckIn(time));
        } else if ("checkout".equalsIgnoreCase(request.action)) {
            if (attendance.getCheckInTime() == null) {
                throw new BadRequestException("Employee must check in before checking out");
            }
            if (attendance.getCheckOutTime() != null) {
                throw new BadRequestException("Employee has already checked out today");
            }
            attendance.setCheckOutTime(time);
        } else {
            throw new BadRequestException("Invalid action. Use 'checkin' or 'checkout'");
        }

        attendance = attendanceRepository.save(attendance);
        return new AttendanceResponse(attendance);
    }

    // Get attendance by ID
    public AttendanceResponse findById(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found: " + id));
        return new AttendanceResponse(attendance);
    }

    // Get attendance for specific employee and date
    public AttendanceResponse findByEmployeeAndDate(Long employeeId, LocalDate date) {
        Attendance attendance = attendanceRepository
                .findByEmployeeIdAndAttendanceDate(employeeId, date)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Attendance record not found for employee " + employeeId + " on " + date));
        return new AttendanceResponse(attendance);
    }

    // Get all attendance records for a specific date
    public List<AttendanceResponse> findByDate(LocalDate date) {
        return attendanceRepository.findByAttendanceDateOrderByEmployeeIdAsc(date)
                .stream()
                .map(AttendanceResponse::new)
                .collect(Collectors.toList());
    }

    // Get all attendance records for a specific employee
    public List<AttendanceResponse> findByEmployee(Long employeeId) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found: " + employeeId);
        }
        return attendanceRepository.findByEmployeeIdOrderByAttendanceDateDesc(employeeId)
                .stream()
                .map(AttendanceResponse::new)
                .collect(Collectors.toList());
    }

    // Get attendance records within a date range
    public List<AttendanceResponse> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return attendanceRepository.findAttendanceByDateRange(startDate, endDate)
                .stream()
                .map(AttendanceResponse::new)
                .collect(Collectors.toList());
    }

    // Get attendance records for an employee within a date range
    public List<AttendanceResponse> findByEmployeeAndDateRange(Long employeeId, LocalDate startDate, LocalDate endDate) {
        if (!employeeRepository.existsById(employeeId)) {
            throw new ResourceNotFoundException("Employee not found: " + employeeId);
        }
        return attendanceRepository.findEmployeeAttendanceByDateRange(employeeId, startDate, endDate)
                .stream()
                .map(AttendanceResponse::new)
                .collect(Collectors.toList());
    }

    // Get attendance summary for an employee within a date range
    public AttendanceSummary getAttendanceSummary(Long employeeId, LocalDate startDate, LocalDate endDate) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + employeeId));

        AttendanceSummary summary = new AttendanceSummary(employeeId, employee.getName(), startDate, endDate);

        List<Attendance> attendanceRecords = attendanceRepository
                .findEmployeeAttendanceByDateRange(employeeId, startDate, endDate);

        // Calculate statistics
        summary.setTotalDays(startDate.datesUntil(endDate.plusDays(1)).count());
        summary.setPresentDays(countByStatus(attendanceRecords, AttendanceStatus.PRESENT));
        summary.setAbsentDays(countByStatus(attendanceRecords, AttendanceStatus.ABSENT));
        summary.setLateDays(countByStatus(attendanceRecords, AttendanceStatus.LATE));
        summary.setHalfDays(countByStatus(attendanceRecords, AttendanceStatus.HALF_DAY));
        summary.setLeaveDays(countByStatus(attendanceRecords, AttendanceStatus.LEAVE));

        // Calculate total and average hours
        double totalHours = attendanceRecords.stream()
                .filter(a -> a.getTotalHours() != null)
                .mapToDouble(Attendance::getTotalHours)
                .sum();

        summary.setTotalHours(totalHours);

        long workingDays = summary.getPresentDays() + summary.getLateDays() + summary.getHalfDays();
        summary.setAverageHours(workingDays > 0 ? totalHours / workingDays : 0);

        return summary;
    }

    // Update attendance record
    public AttendanceResponse updateAttendance(Long id, AttendanceRequest request) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found: " + id));

        if (request.checkInTime != null) {
            attendance.setCheckInTime(request.checkInTime);
        }
        if (request.checkOutTime != null) {
            attendance.setCheckOutTime(request.checkOutTime);
        }
        if (request.status != null) {
            attendance.setStatus(request.status);
        }
        if (request.notes != null) {
            attendance.setNotes(request.notes);
        }

        attendance = attendanceRepository.save(attendance);
        return new AttendanceResponse(attendance);
    }

    // Delete attendance record
    public void deleteAttendance(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Attendance record not found: " + id));
        attendanceRepository.delete(attendance);
    }

    // Helper method to determine status from check-in time
    private AttendanceStatus determineStatusFromCheckIn(LocalTime checkInTime) {
        LocalTime standardStartTime = LocalTime.of(9, 0); // 9:00 AM
        LocalTime lateThreshold = LocalTime.of(9, 30);    // 9:30 AM

        if (checkInTime.isAfter(lateThreshold)) {
            return AttendanceStatus.LATE;
        } else {
            return AttendanceStatus.PRESENT;
        }
    }

    // Helper method to count attendance by status
    private long countByStatus(List<Attendance> attendanceRecords, AttendanceStatus status) {
        return attendanceRecords.stream()
                .filter(a -> a.getStatus() == status)
                .count();
    }
}