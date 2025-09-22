package com.example.ems.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    public String username;
    
    @NotBlank
    @Size(min = 4, max = 100)
    public String password;
}