package com.example.newsfeedproject.dto.comment;

import com.example.newsfeedproject.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long id;

    private Long userId;

    private Long PostId;

    private String userName;

    private String content;

    private int likeCount;

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
    public static CommentResponseDto fromEntity(Comment comment) {
        CommentResponseDto dto = new CommentResponseDto();

        dto.id = comment.getId();
        dto.userId = comment.getUser().getId();
        dto.PostId = comment.getPost().getId();
        dto.userName = comment.getWriteComment();
        dto.likeCount = comment.getLikeCount();

        return dto;
    }
}
