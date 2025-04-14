package com.unitwin.model;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class Post {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private List<String> tags;

    public Post() {}

    public Post(Long id, String title, String content, String author, LocalDateTime createdAt, List<String> tags) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.tags = tags;
    }
}
