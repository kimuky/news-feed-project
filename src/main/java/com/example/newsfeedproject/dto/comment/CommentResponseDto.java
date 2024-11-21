package com.example.newsfeedproject.dto.comment;

import com.example.newsfeedproject.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;

    private String commentWriter;

    private String writeComment;

    public static CommentResponseDto fromEntity(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();

        dto.id = comment.getId();
        dto.writeComment = comment.getWriteComment();
        dto.commentWriter = comment.getUser().getName();

        return dto;
    }
}
