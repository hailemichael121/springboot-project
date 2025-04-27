package com.example.basicsprboot2025.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultRestController {

    @GetMapping("/")
    public String home() {
        return "Welcome to Spring Boot Yihun!";
    }
}