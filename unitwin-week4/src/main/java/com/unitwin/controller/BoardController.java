package com.unitwin.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boards")
public class BoardController {

    private List<Board> boards = new ArrayList<>();
    private Long nextId = 1L;

    public BoardController() {
        // Initialize with sample data
        boards.add(new Board(1L, "First Post", "Hello World!", "admin", LocalDateTime.now(), 0));
    }

    @GetMapping
    public String listBoards(Model model) {
        model.addAttribute("boards", boards);
        return "board/list";
    }

    @GetMapping("/{id}")
    public String viewBoard(@PathVariable Long id, Model model) {
        Board board = boards.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid board ID"));
        model.addAttribute("board", board);
        return "board/view";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("board", new Board());
        return "board/form";
    }

    @PostMapping
    public String createBoard(@ModelAttribute Board board) {
        board.setId(nextId++);
        board.setCreatedAt(LocalDateTime.now());
        board.setViewCount(0);
        boards.add(board);
        return "redirect:/boards";
    }
}