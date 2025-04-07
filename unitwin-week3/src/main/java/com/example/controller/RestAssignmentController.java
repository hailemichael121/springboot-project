package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/assignment2")
public class RestAssignmentController {

    @GetMapping
    public Map<String, Object> assignment2() {
        Map<String, Object> response = new HashMap<>();
        response.put("assignment", "Week 3 - Part 2");
        response.put("student", "Yihun Shekuri");
        response.put("date", "April 2024");
        response.put("description", "This is a Map object containing multiple pieces of information");
        response.put("status", "SUCCESS");
        return response;
    }
}