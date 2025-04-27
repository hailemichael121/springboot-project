package com.example.basicsprboot2025.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.basicsprboot2025.model.Board;

@RestController
@RequestMapping("/api/boards")
public class BoardRestController {

    private final Map<Long, Board> boards = new HashMap<>();

    public BoardRestController() {
        initializeSampleData();
    }

private void initializeSampleData() {
    Board sampleBoard = new Board(1L, "Getting Started with Spring Boot", 
        "Learn how to create your first Spring Boot application");
    sampleBoard.setAuthor("Prof. Smith");
    sampleBoard.setCategory("Tutorial");
    
    boards.put(sampleBoard.getId(), sampleBoard);
}

    @GetMapping
    public ResponseEntity<List<Board>> getAllBoards() {
        return ResponseEntity.ok(new ArrayList<>(boards.values()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable Long id) {
        Board board = boards.get(id);
        if (board == null) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Board not found with id: " + id
            );
        }
        return ResponseEntity.ok(board);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleException(ResponseStatusException ex) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", ex.getStatusCode().value());
        body.put("error", ex.getReason());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, ex.getStatusCode());
    }
}
