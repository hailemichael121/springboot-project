package com.example.basicsprboot2025.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.basicsprboot2025.exception.ResourceNotFoundException;
import com.example.basicsprboot2025.model.Board;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/boards")
public class BoardController {

    private final Map<Long, Board> boards = new HashMap<>();

    public BoardController() {
        initializeSampleData();
    }

    private void initializeSampleData() {
        Board board1 = new Board(1L, "Spring Boot Basics", "Introduction to Spring Boot");
        board1.setAuthor("Prof. Smith");
        board1.setCategory("Tutorial");
        
        Board board2 = new Board(2L, "Advanced Spring", "Deep dive into Spring Framework");
        board2.setAuthor("Dr. Johnson");
        board2.setCategory("Advanced");
        
        boards.put(board1.getId(), board1);
        boards.put(board2.getId(), board2);
    }

    @GetMapping
    public String listBoards(Model model) {
        model.addAttribute("boards", new ArrayList<>(boards.values()));
        model.addAttribute("currentTime", LocalDateTime.now());
        return "board/list";
    }

    @GetMapping("/{id}")
    public String viewBoard(@PathVariable Long id, Model model) {
        Board board = boards.get(id);
        if (board == null) {
            throw new ResourceNotFoundException("Board", "id", id);
        }
        model.addAttribute("board", board);
        return "board/view";
    }

    @GetMapping("/new")
    public String newBoardForm(Model model) {
        model.addAttribute("board", new Board());
        return "board/new";
    }

    @PostMapping
    public String createBoard(@Valid @ModelAttribute Board board, BindingResult result) {
        if (result.hasErrors()) {
            return "board/new";
        }
        
        long newId = boards.keySet().stream().max(Long::compare).orElse(0L) + 1;
        board.setId(newId);
        board.setCreatedAt(LocalDateTime.now());
        board.setUpdatedAt(LocalDateTime.now());
        board.setViewCount(0);
        
        boards.put(board.getId(), board);
        return "redirect:/boards/" + board.getId();
    }

    @GetMapping("/{id}/edit")
    public String editBoardForm(@PathVariable Long id, Model model) {
        Board board = boards.get(id);
        if (board == null) {
            throw new ResourceNotFoundException("Board", "id", id);
        }
        model.addAttribute("board", board);
        return "board/edit";
    }

    @PostMapping("/{id}")
    public String updateBoard(@PathVariable Long id, @ModelAttribute Board boardUpdates) {
        Board existingBoard = boards.get(id);
        if (existingBoard == null) {
            throw new ResourceNotFoundException("Board", "id", id);
        }
        
        existingBoard.updateFrom(boardUpdates);
        return "redirect:/boards/" + id + "?updated=true";
    }

    @PostMapping("/{id}/delete")
    public String deleteBoard(@PathVariable Long id) {
        if (!boards.containsKey(id)) {
            throw new ResourceNotFoundException("Board", "id", id);
        }
        boards.remove(id);
        return "redirect:/boards?deleted=true";
    }

    @GetMapping("/search")
    public String searchBoards(
        @RequestParam(required = false) String query,
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String content,
        @RequestParam(required = false) String author,
        @RequestParam(required = false) String category,
        Model model) {
        
        List<Board> results = boards.values().stream()
            .filter(board -> 
                (query == null || 
                 board.getTitle().toLowerCase().contains(query.toLowerCase()) ||
                 board.getContent().toLowerCase().contains(query.toLowerCase())) &&
                (title == null || board.getTitle().toLowerCase().contains(title.toLowerCase())) &&
                (content == null || board.getContent().toLowerCase().contains(content.toLowerCase())) &&
                (author == null || board.getAuthor().toLowerCase().contains(author.toLowerCase())) &&
                (category == null || board.getCategory().equalsIgnoreCase(category)))
            .collect(Collectors.toList());
        
        model.addAttribute("boards", results);
        model.addAttribute("currentTime", LocalDateTime.now());
        model.addAttribute("query", query);
        return "board/list";
    }

    @GetMapping("/search/advanced")
    public String advancedSearchForm(Model model) {
        List<String> categories = Arrays.asList("Tutorial", "News", "Discussion", "Question");
        model.addAttribute("categories", categories);
        return "board/search";
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ModelAndView handleResourceNotFound(ResourceNotFoundException ex) {
        ModelAndView mav = new ModelAndView("board/error");
        mav.addObject("errorTitle", "Resource Not Found");
        mav.addObject("errorMessage", ex.getMessage());
        mav.addObject("timestamp", LocalDateTime.now());
        mav.setStatus(HttpStatus.NOT_FOUND);
        return mav;
    }
}