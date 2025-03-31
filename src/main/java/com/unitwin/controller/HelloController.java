package com.unitwin.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class HelloController {

    // GET method
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, Spring Boot!";
    }

    // POST method
    @PostMapping("/hello")
    public String createMessage(@RequestBody String message) {
        return "Message received: " + message;
    }

    // PUT method
    @PutMapping("/hello/{id}")
    public String updateMessage(@PathVariable int id, @RequestBody String message) {
        return "Updated message with ID " + id + ": " + message;
    }

    // DELETE method
    @DeleteMapping("/hello/{id}")
    public String deleteMessage(@PathVariable int id) {
        return "Deleted message with ID " + id;
    }
}