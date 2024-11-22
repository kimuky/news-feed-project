package com.example.newsfeedproject.dto.friend;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class FriendRequestDto {

    @NotBlank
    @Email
    private final String userEmail;

    @JsonCreator
    public FriendRequestDto(String userEmail) {
        this.userEmail = userEmail;
    }
}
