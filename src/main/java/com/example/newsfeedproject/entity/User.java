package com.example.newsfeedproject.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "bit")
    private int age;

    private String introduce;

    @Column(nullable = false)
    private int activated;

    private LocalDateTime deletedAt;

    public User() {
    }
}
