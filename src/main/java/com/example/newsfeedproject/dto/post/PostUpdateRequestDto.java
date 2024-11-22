package com.example.newsfeedproject.dto.post;

import lombok.Getter;

@Getter
public class PostUpdateRequestDto {

    private final Long userId;

    private final String title;

    private final String content;


    public PostUpdateRequestDto(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
