package com.example.newsfeedproject.dto.user;

import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

    private final String name;

    private final int age;

    private final String introduce;

    private final String password;

    public UpdateUserRequestDto(String name, int age, String introduce, String password) {
        this.name = name;
        this.age = age;
        this.introduce = introduce;
        this.password = password;
    }
}
