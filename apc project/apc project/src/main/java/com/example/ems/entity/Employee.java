// package com.example.ems;

// import jakarta.persistence.*;
// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Pattern;
// import jakarta.validation.constraints.Size;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "employees")
// public class Employee {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @NotBlank
//     @Size(max = 100)
//     private String name;

//     @Email
//     @Size(max = 150)
//     @Column(unique = true)
//     private String email;

//     @NotBlank
//     @Size(max = 100)
//     private String designation;

//     @NotBlank
//     @Size(max = 100)
//     private String department;

//     @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone must be 10-15 digits")
//     @Column(length = 20)
//     private String phone;

//     private Double salary;

//     private LocalDateTime createdAt;
//     private LocalDateTime updatedAt;

//     @PrePersist
//     public void onCreate() {
//         createdAt = LocalDateTime.now();
//         updatedAt = createdAt;
//     }

//     @PreUpdate
//     public void onUpdate() {
//         updatedAt = LocalDateTime.now();
//     }

//     // Getters and setters
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }

//     public String getName() { return name; }
//     public void setName(String name) { this.name = name; }

//     public String getEmail() { return email; }
//     public void setEmail(String email) { this.email = email; }

//     public String getDesignation() { return designation; }
//     public void setDesignation(String designation) { this.designation = designation; }

//     public String getDepartment() { return department; }
//     public void setDepartment(String department) { this.department = department; }

//     public String getPhone() { return phone; }
//     public void setPhone(String phone) { this.phone = phone; }

//     public Double getSalary() { return salary; }
//     public void setSalary(Double salary) { this.salary = salary; }

//     public LocalDateTime getCreatedAt() { return createdAt; }
//     public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

//     public LocalDateTime getUpdatedAt() { return updatedAt; }
//     public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
// }



package com.example.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "name", nullable = false)
    private String name;

    @Email
    @Size(max = 150)
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @NotBlank
    @Size(max = 100)
    @Column(name = "designation", nullable = false)
    private String designation;

    @NotBlank
    @Size(max = 100)
    @Column(name = "department", nullable = false)
    private String department;

    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone must be 10-15 digits")
    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "salary")
    private Double salary;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = createdAt;
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public Employee() {}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public Double getSalary() { return salary; }
    public void setSalary(Double salary) { this.salary = salary; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
