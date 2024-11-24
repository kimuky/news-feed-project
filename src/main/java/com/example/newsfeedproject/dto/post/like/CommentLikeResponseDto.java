package com.example.newsfeedproject.dto.post.like;

import com.example.newsfeedproject.entity.CommentLike;
import lombok.Getter;

@Getter
public class CommentLikeResponseDto {

    private Long id;

    private Long userId;

    private Long commentId;

    private String message;

    public static CommentLikeResponseDto fromEntity(CommentLike commentLike) {
        CommentLikeResponseDto commentLikeResponseDto = new CommentLikeResponseDto();
        commentLikeResponseDto.id = commentLike.getId();
        commentLikeResponseDto.userId = commentLike.getUser().getId();
        commentLikeResponseDto.commentId = commentLike.getComment().getId();
        return commentLikeResponseDto;

    }

    public void setMessage(String message) {
        this.message = message;
    }

}
