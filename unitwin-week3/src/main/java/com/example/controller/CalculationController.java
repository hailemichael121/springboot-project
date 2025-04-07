package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CalculationController {

    @GetMapping("/sum-form")
    public String showForm() {
        return "sum-form"; // Shows the input form
    }

    @PostMapping("/sum")
    public String calculateSum(
            @RequestParam int num1,
            @RequestParam int num2,
            @RequestParam int num3,
            Model model) {
        
        int sum = num1 + num2 + num3;
        
        model.addAttribute("num1", num1);
        model.addAttribute("num2", num2);
        model.addAttribute("num3", num3);
        model.addAttribute("result", sum);
        
        return "sum-result"; // Shows the result page
    }
}