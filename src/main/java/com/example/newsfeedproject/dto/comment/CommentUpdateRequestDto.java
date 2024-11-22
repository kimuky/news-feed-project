package com.example.newsfeedproject.dto.comment;


import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

    private final Long userId;

    private final String writeComment;

    public CommentUpdateRequestDto(Long userId, String writeComment) {
        this.userId = userId;
        this.writeComment = writeComment;
    }
}
