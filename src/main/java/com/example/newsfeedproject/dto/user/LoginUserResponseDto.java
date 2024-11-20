package com.example.newsfeedproject.dto.user;

import com.example.newsfeedproject.entity.User;
import lombok.Getter;

@Getter
public class LoginUserResponseDto {
    private final Long id;
    private final String email;

    public LoginUserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
    }
}
