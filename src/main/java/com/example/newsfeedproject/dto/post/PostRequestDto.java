package com.example.newsfeedproject.dto.post;


import lombok.Getter;

@Getter
public class PostRequestDto {

    private final Long userId;

    private final String title;

    private final String content;

    public PostRequestDto(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
