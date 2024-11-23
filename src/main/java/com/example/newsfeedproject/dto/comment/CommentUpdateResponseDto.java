package com.example.newsfeedproject.dto.comment;


import com.example.newsfeedproject.entity.Comment;
import com.example.newsfeedproject.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentUpdateResponseDto {

    private final Long id;

    private final Long userId;

    private final Long postId;

    private final String userName;

    private final String content;

    private final int likeCount;

    private final LocalDateTime createdAt;

    private final LocalDateTime updatedAt;

    public CommentUpdateResponseDto(User findUser, Comment findComment,Long postId, CommentRequestDto requestDto) {
        this.id = findComment.getId();
        this.userId = findUser.getId();
        this.postId = postId;
        this.userName = findUser.getName();
        this.content = requestDto.getWriteComment();
        this.likeCount = findComment.getLikeCount();
        this.createdAt = findComment.getCreatedAt();
        this.updatedAt = LocalDateTime.now();
    }
}
