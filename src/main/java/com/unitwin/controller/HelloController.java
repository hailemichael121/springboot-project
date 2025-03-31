package com.unitwin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.Valid;  
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class HelloController {

    private final Map<Integer, String> messages = new HashMap<>();
    private int nextId = 1;

    @GetMapping("/hello")
    public ResponseEntity<ApiResponse<String>> sayHello() {
        return ResponseEntity.ok(
            new ApiResponse<>("Success", "Hello, Spring Boot!")
        );
    }

    @PostMapping("/hello")
    public ResponseEntity<ApiResponse<Integer>> createMessage(
            @Valid @RequestBody MessageRequest request) {
        int id = nextId++;
        messages.put(id, request.getMessage());
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(new ApiResponse<>("Created", id));
    }

    @GetMapping("/hello/{id}")
    public ResponseEntity<ApiResponse<String>> getMessage(
            @PathVariable int id) {
        if (!messages.containsKey(id)) {
            throw new ResourceNotFoundException("Message not found with id: " + id);
        }
        return ResponseEntity.ok(
            new ApiResponse<>("Success", messages.get(id))
        );
    }

    @PutMapping("/hello/{id}")
    public ResponseEntity<ApiResponse<String>> updateMessage(
            @PathVariable int id,
            @Valid @RequestBody MessageRequest request) {
        if (!messages.containsKey(id)) {
            throw new ResourceNotFoundException("Message not found with id: " + id);
        }
        messages.put(id, request.getMessage());
        return ResponseEntity.ok(
            new ApiResponse<>("Updated", "Message updated successfully")
        );
    }

    @DeleteMapping("/hello/{id}")
    public ResponseEntity<ApiResponse<String>> deleteMessage(
            @PathVariable int id) {
        if (!messages.containsKey(id)) {
            throw new ResourceNotFoundException("Message not found with id: " + id);
        }
        messages.remove(id);
        return ResponseEntity.ok(
            new ApiResponse<>("Deleted", "Message deleted successfully")
        );
    }

    // Supporting classes
    static class MessageRequest {
        @NotBlank(message = "Message cannot be blank")
        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    static class ApiResponse<T> {
        private String status;
        private T data;

        public ApiResponse(String status, T data) {
            this.status = status;
            this.data = data;
        }

        public String getStatus() {
            return status;
        }

        public T getData() {
            return data;
        }
    }

    static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String message) {
            super(message);
        }
    }

    @ControllerAdvice
    public static class GlobalExceptionHandler {
        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ApiResponse<String>> handleResourceNotFound(
                ResourceNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("Error", ex.getMessage()));
        }

        @ExceptionHandler(MethodArgumentTypeMismatchException.class)
        public ResponseEntity<ApiResponse<String>> handleTypeMismatch() {
            return ResponseEntity.badRequest()
                .body(new ApiResponse<>("Error", "Invalid ID format"));
        }

        @ExceptionHandler(Exception.class)
        public ResponseEntity<ApiResponse<String>> handleGeneralException() {
            return ResponseEntity.internalServerError()
                .body(new ApiResponse<>("Error", "An unexpected error occurred"));
        }
    }
}