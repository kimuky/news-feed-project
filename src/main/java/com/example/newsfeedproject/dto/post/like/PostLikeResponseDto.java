package com.example.newsfeedproject.dto.post.like;

import com.example.newsfeedproject.entity.PostLike;
import lombok.Getter;

@Getter
public class PostLikeResponseDto {

    private Long id;

    private Long postId;

    private Long userId;

    private String message;

    public static PostLikeResponseDto fromEntity(PostLike postLike) {
        PostLikeResponseDto postLikeResponseDto = new PostLikeResponseDto();

        postLikeResponseDto.id = postLike.getId();
        postLikeResponseDto.postId = postLike.getPost().getId();
        postLikeResponseDto.userId = postLike.getUser().getId();

        return postLikeResponseDto;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
