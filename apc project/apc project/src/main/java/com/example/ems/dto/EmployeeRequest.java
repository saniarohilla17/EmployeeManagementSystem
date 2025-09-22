package com.example.ems.dto;

import jakarta.validation.constraints.*;

public class EmployeeRequest {
    @NotBlank @Size(max = 100)
    public String name;

    @Email @Size(max = 150)
    public String email;

    @NotBlank @Size(max = 100)
    public String designation;

    @NotBlank @Size(max = 100)
    public String department;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone must be 10-15 digits")
    public String phone;

    @PositiveOrZero
    public Double salary;
}
