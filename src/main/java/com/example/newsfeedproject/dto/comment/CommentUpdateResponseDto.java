package com.example.newsfeedproject.dto.comment;


import com.example.newsfeedproject.entity.Comment;
import lombok.Getter;

@Getter
public class CommentUpdateResponseDto {

    private long id;

    private String commentWriter;

    private String writeComment;

    public static CommentUpdateResponseDto fromEntity(Comment comment) {
        CommentUpdateResponseDto dto = new CommentUpdateResponseDto();

        dto.id = comment.getId();
        dto.commentWriter = comment.getUser().getName();
        dto.writeComment = comment.getWriteComment();

        return dto;
    }

}
