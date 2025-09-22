package com.example.ems.repository;

import com.example.ems.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.ems.entity.AttendanceStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    Optional<Attendance> findByEmployeeIdAndAttendanceDate(Long employeeId, LocalDate attendanceDate);

    List<Attendance> findByEmployeeIdOrderByAttendanceDateDesc(Long employeeId);

    List<Attendance> findByAttendanceDateOrderByEmployeeIdAsc(LocalDate attendanceDate);

    List<Attendance> findByEmployeeIdAndAttendanceDateBetweenOrderByAttendanceDateDesc(
            Long employeeId, LocalDate startDate, LocalDate endDate);

    List<Attendance> findByAttendanceDateBetweenOrderByAttendanceDateDescEmployeeIdAsc(
            LocalDate startDate, LocalDate endDate);

    @Query("SELECT a FROM Attendance a WHERE a.attendanceDate BETWEEN :startDate AND :endDate ORDER BY a.attendanceDate DESC, a.employee.name ASC")
    List<Attendance> findAttendanceByDateRange(@Param("startDate") LocalDate startDate,
                                               @Param("endDate") LocalDate endDate);

    @Query("SELECT a FROM Attendance a WHERE a.employee.id = :employeeId AND a.attendanceDate BETWEEN :startDate AND :endDate ORDER BY a.attendanceDate DESC")
    List<Attendance> findEmployeeAttendanceByDateRange(@Param("employeeId") Long employeeId,
                                                       @Param("startDate") LocalDate startDate,
                                                       @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(a) FROM Attendance a WHERE a.employee.id = :employeeId AND a.status = :status AND a.attendanceDate BETWEEN :startDate AND :endDate")
    Long countByEmployeeIdAndStatusAndDateRange(@Param("employeeId") Long employeeId,
                                                @Param("status") AttendanceStatus status,
                                                @Param("startDate") LocalDate startDate,
                                                @Param("endDate") LocalDate endDate);
}