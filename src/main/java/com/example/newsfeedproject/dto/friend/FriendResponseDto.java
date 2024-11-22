package com.example.newsfeedproject.dto.friend;

import lombok.Data;

@Data
public class FriendResponseDto {

    private Long id;

    private String name;

    public FriendResponseDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static FriendResponseDto toDto (FriendResponseDto testDto) {
        return new FriendResponseDto(testDto.getId(), testDto.getName());
    }
}
