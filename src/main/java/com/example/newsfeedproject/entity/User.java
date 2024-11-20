package com.example.newsfeedproject.entity;

import com.example.newsfeedproject.dto.user.RegisterUserRequestDto;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Entity
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private int age;

    private String introduce;

    @Column(nullable = false, columnDefinition = "bit")
    private int activated;

    private LocalDateTime deletedAt;

    public User() {
    }

    public User(RegisterUserRequestDto requestDto, String encodePassword) {
        this.email = requestDto.getEmail();
        this.password = encodePassword;
        this.name = requestDto.getName();
        this.age = requestDto.getAge();
        this.activated = 1;
    }
}
