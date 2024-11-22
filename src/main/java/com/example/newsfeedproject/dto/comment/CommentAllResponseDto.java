package com.example.newsfeedproject.dto.comment;

import com.example.newsfeedproject.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentAllResponseDto {

    private final Long id;

    private final Long userId;

    private final Long postId;

    private final String userName;

    private final String content;

    private final int likeCount;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public CommentAllResponseDto(Comment comment) {
        this.id = comment.getId();
        this.userId = comment.getUser().getId();
        this.postId = comment.getPost().getId();
        this.userName = comment.getUser().getName();
        this.content = comment.getWriteComment();
        this.likeCount = comment.getLikeCount();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
