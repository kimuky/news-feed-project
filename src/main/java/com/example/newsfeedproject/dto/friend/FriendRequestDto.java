package com.example.newsfeedproject.dto.friend;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public class FriendRequestDto {

    private final String userEmail;

    @JsonCreator
    public FriendRequestDto(String userEmail) {
        this.userEmail = userEmail;
    }
}
