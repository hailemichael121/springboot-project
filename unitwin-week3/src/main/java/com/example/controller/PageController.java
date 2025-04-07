package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/assignment1")
    public String assignment1(Model model) {
        model.addAttribute("message", "This is the response for Week 3 Assignment - Part 1");
        return "assignment1";
    }
}