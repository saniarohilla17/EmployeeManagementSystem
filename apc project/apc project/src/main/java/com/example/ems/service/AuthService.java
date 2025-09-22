package com.example.ems.service;

import com.example.ems.security.JwtUtil;
import com.example.ems.dto.AuthRequest;
import com.example.ems.dto.AuthResponse;
import com.example.ems.entity.User;
import com.example.ems.exception.BadRequestException;
import com.example.ems.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }
    
    public AuthResponse register(AuthRequest request) {
        if (userRepository.existsByUsername(request.username)) {
            throw new BadRequestException("Username already exists: " + request.username);
        }
        
        User user = new User(request.username, passwordEncoder.encode(request.password));
        userRepository.save(user);
        
        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername());
    }
    
    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByUsername(request.username)
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));
        
        if (!passwordEncoder.matches(request.password, user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }
        
        String token = jwtUtil.generateToken(user.getUsername());
        return new AuthResponse(token, user.getUsername());
    }
}