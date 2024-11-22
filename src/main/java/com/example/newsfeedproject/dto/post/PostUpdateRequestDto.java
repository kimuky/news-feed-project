package com.example.newsfeedproject.dto.post;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostUpdateRequestDto {

    private final Long userId;

    @NotBlank(message = "제목을 꼭 입력해주세요")
    private final String title;

    @NotBlank(message = "내용을 꼭 입력해주세요")
    private final String content;


    public PostUpdateRequestDto(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }
}
