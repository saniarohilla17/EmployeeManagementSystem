// package com.example.ems;

// import jakarta.persistence.*;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Size;
// import java.time.LocalDateTime;

// @Entity
// @Table(name = "users")
// public class User {
    
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
    
//     @NotBlank
//     @Size(max = 50)
//     @Column(unique = true)
//     private String username;
    
//     @NotBlank
//     @Size(max = 120)
//     private String password;
    
//     private LocalDateTime createdAt;
    
//     @PrePersist
//     public void onCreate() {
//         createdAt = LocalDateTime.now();
//     }
    
//     // Constructors
//     public User() {}
    
//     public User(String username, String password) {
//         this.username = username;
//         this.password = password;
//     }
    
//     // Getters and setters
//     public Long getId() { return id; }
//     public void setId(Long id) { this.id = id; }
    
//     public String getUsername() { return username; }
//     public void setUsername(String username) { this.username = username; }
    
//     public String getPassword() { return password; }
//     public void setPassword(String password) { this.password = password; }
    
//     public LocalDateTime getCreatedAt() { return createdAt; }
//     public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
// }



package com.example.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    
    @NotBlank
    @Size(max = 50)
    @Column(name = "username", unique = true, nullable = false)
    private String username;
    
    @NotBlank
    @Size(max = 120)
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }
    
    // Constructors
    public User() {}
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}