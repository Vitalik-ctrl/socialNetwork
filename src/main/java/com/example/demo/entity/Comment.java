package com.example.demo.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @ManyToOne(fetch = FetchType.EAGER)
    private Post post;
    @Column(updatable = false)
    private LocalDateTime createdDate;
    @Column(columnDefinition = "text", nullable = false)
    private String message;
    @Column(nullable = false)
    private Long userId;


    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}
