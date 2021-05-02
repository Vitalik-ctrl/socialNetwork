package com.example.entity;

import javax.persistence.PrePersist;
import java.time.LocalDateTime;

public class Comment {
    private Long id;
    private String username;
    private Post post;
    private LocalDateTime createdDate;
    private String message;
    private Long userId;


    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}
