package com.example.newsfeedproject.dto.user;

import lombok.Getter;

@Getter
public class RegisterUserRequestDto {

    private final String email;

    private final String password;

    private final String name;

    private final int age;

    public RegisterUserRequestDto(String email, String password, String name, int age) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
    }
}
