package com.unitwin.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unitwin.model.Board;

@RestController
@RequestMapping("/api/boards")
public class BoardRestController {

    private List<Board> boards = new ArrayList<>();
    private Long nextId = 1L;

    public BoardRestController() {
        boards.add(new Board(1L, "First Post", "Hello World!", "admin", LocalDateTime.now(), 0));
    }

    @GetMapping
    public List<Board> getAllBoards() {
        return boards;
    }

    @GetMapping("/{id}")
    public Board getBoardById(@PathVariable Long id) {
        return boards.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
    }

    @PostMapping
    public Board createBoard(@RequestBody Board board) {
        board.setId(nextId++);
        board.setCreatedAt(LocalDateTime.now());
        board.setViewCount(0);
        boards.add(board);
        return board;
    }
}
