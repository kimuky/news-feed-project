package com.example.newsfeedproject.dto.comment;

import com.example.newsfeedproject.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private final Long id;

    private final Long userId;

    private final Long PostId;

    private final String userName;

    private final String content;

    private final int likeCount;

    private LocalDateTime createdAt;

    public CommentResponseDto(Comment findComment) {
        this.id = findComment.getId();
        this.userId = findComment.getUser().getId();
        this.PostId = findComment.getPost().getId();
        this.userName = findComment.getUser().getName();
        this.content = findComment.getWriteComment();
        this.likeCount = findComment.getLikeCount();
        this.createdAt = LocalDateTime.now();
    }
}
