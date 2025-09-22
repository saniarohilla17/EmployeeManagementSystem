package com.example.ems.controller;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Controller
public class WebController {
    
    @GetMapping("/")
    public ResponseEntity<String> index() {
        return serveHtmlFile("static/index.html");
    }
    
    @GetMapping("/index.html")
    public ResponseEntity<String> indexHtml() {
        return serveHtmlFile("static/index.html");
    }
    
    @GetMapping("/login.html")
    public ResponseEntity<String> loginHtml() {
        return serveHtmlFile("static/login.html");
    }
    
    private ResponseEntity<String> serveHtmlFile(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            String content = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_HTML)
                    .body(content);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }
}