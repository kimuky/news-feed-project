package com.example.newsfeedproject.dto.comment;

import com.example.newsfeedproject.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;

    private Long userId;

    private Long PostId;

    private String userName;

    private String content;

    private int likeCount;

    private LocalDateTime createdAt;

    public CommentResponseDto() {
    }

    public CommentResponseDto(Long id, Long userId, Long PostId, String userName, String content, int likeCount) {
        this.id = id;
        this.userId = userId;
        this.PostId = PostId;
        this.userName = userName;
        this.content = content;
        this.likeCount = likeCount;
    }

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
