package com.example.newsfeedproject.dto.comment;

import com.example.newsfeedproject.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;

    private String commentWriter;

    private String writeComment;

    public CommentResponseDto() {
    }

    public CommentResponseDto(Long id, String commentWriter, String writeComment) {
        this.id = id;
        this.commentWriter = commentWriter;
        this.writeComment = writeComment;
    }

    public static CommentResponseDto fromEntity(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();

        dto.id = comment.getId();
        dto.writeComment = comment.getWriteComment();
        dto.commentWriter = comment.getUser().getName();

        return dto;
    }

    public static CommentResponseDto toDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getUser().getName(),
                comment.getWriteComment()
        );
    }
}
