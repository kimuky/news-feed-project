package com.example.newsfeedproject.dto.comment;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private final Long userId;

    private final String writeComment;

    public CommentRequestDto(Long userId, String writeComment) {
        this.userId = userId;
        this.writeComment = writeComment;
    }
}
