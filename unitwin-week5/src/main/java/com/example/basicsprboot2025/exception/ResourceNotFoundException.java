// src/main/java/com/example/basicsprboot2025/exception/ResourceNotFoundException.java
package com.example.basicsprboot2025.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s: %s", 
            resourceName, fieldName, fieldValue));
    }
}