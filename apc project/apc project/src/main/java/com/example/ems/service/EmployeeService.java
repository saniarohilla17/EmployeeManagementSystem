package com.example.ems.service;

import com.example.ems.dto.EmployeeRequest;
import com.example.ems.entity.Employee;
import com.example.ems.exception.BadRequestException;
import com.example.ems.exception.ResourceNotFoundException;
import com.example.ems.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public List<Employee> findAll() { return repository.findAll(); }

    public Employee findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Employee not found: " + id));
    }

    public Employee create(EmployeeRequest req) {
        repository.findByEmail(req.email).ifPresent(e -> {
            throw new BadRequestException("Email already exists: " + req.email);
        });
        Employee e = new Employee();
        apply(e, req);
        return repository.save(e);
    }

    public Employee update(Long id, EmployeeRequest req) {
        Employee e = findById(id);
        if (req.email != null && !req.email.equals(e.getEmail())) {
            repository.findByEmail(req.email).ifPresent(other -> {
                throw new BadRequestException("Email already exists: " + req.email);
            });
        }
        apply(e, req);
        return repository.save(e);
    }

    public void delete(Long id) {
        Employee e = findById(id);
        repository.delete(e);
    }

    private void apply(Employee e, EmployeeRequest req) {
        if (req.name != null) e.setName(req.name);
        if (req.email != null) e.setEmail(req.email);
        if (req.designation != null) e.setDesignation(req.designation);
        if (req.department != null) e.setDepartment(req.department);
        if (req.phone != null) e.setPhone(req.phone);
        if (req.salary != null) e.setSalary(req.salary);
    }
}
