package com.example.newsfeedproject.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    private final Long userId;

    @NotBlank(message = "내용을 입력해주세요.")
    private final String writeComment;

    public CommentRequestDto(Long userId, String writeComment) {
        this.userId = userId;
        this.writeComment = writeComment;
    }
}
