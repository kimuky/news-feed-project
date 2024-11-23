package com.example.newsfeedproject.dto.friend;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FriendRequestDto {

    @NotBlank(message = "이메일을 꼭 입력해주세요")
    @Email (message = "이메일 형식을 맞춰주세요")
    private final String email;

    @JsonCreator
    public FriendRequestDto(String email) {
        this.email = email;
    }
}
