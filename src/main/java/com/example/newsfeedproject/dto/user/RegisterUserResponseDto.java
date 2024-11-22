package com.example.newsfeedproject.dto.user;

import com.example.newsfeedproject.entity.User;
import lombok.Getter;

@Getter
public class RegisterUserResponseDto {

    private final Long id;

    private final String email;

    private final String name;

    private final int age;

    private final int activated;

    public RegisterUserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.age = user.getAge();
        this.activated = user.getActivated();
    }
}
