package com.example.newsfeedproject.dto.friend;

import lombok.Getter;

@Getter
public class FriendListResponseDto {

    private final Long id;

    private final String name;

    public FriendListResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
