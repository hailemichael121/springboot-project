package com.example.basicsprboot2025.model;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Board {
     @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title must be less than 100 characters")
    private String title;
    
    @NotBlank(message = "Content is required")
    private String content;
    private Long id;
    private String author = "Anonymous"; // Default value
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
    private int viewCount = 0;
    private String category = "General";

    // No-arg constructor
    public Board() {}

    // Constructor for minimal initialization
    public Board(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    // Full constructor
    public Board(Long id, String title, String content, String author, 
                String category, int viewCount) {
        this(id, title, content);
        this.author = author;
        this.category = category;
        this.viewCount = viewCount;
    }


    // Getters
    public Long getId() {   
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    public int getViewCount() {
        return viewCount;
    }
    public String getCategory() {
        return category;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
     public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public void updateFrom(Board other) {
    this.title = other.getTitle();
    this.content = other.getContent();
    this.author = other.getAuthor();
    this.category = other.getCategory();
    this.updatedAt = LocalDateTime.now();
}

}
