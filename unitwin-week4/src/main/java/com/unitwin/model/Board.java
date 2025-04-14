package com.unitwin.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Board {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime createdAt;
    private int viewCount;

    public Board() {}

    public Board(Long id, String title, String content, String author, LocalDateTime createdAt, int viewCount) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.createdAt = createdAt;
        this.viewCount = viewCount;
    }
}
