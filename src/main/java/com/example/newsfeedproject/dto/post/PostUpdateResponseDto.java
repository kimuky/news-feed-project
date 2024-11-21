package com.example.newsfeedproject.dto.post;

import com.example.newsfeedproject.entity.Post;
import lombok.Getter;

@Getter
public class PostUpdateResponseDto {
    private Long id;
    private String title;
    private String content;//작성자 이름
    private String createdDate; //작성일
    private String modifiedDate;
    private String message;
    private int likeCount;

    //Post Entity -> Dto 변환
    public static PostUpdateResponseDto fromEntity(Post post){
        PostUpdateResponseDto dto = new PostUpdateResponseDto();

        dto.id = post.getId();
        dto.title = post.getTitle();
        dto.content = post.getContent();
        dto.createdDate = post.getCreatedAt().toString(); //작성일을 문자열로 변환
        dto.modifiedDate = post.getUpdatedAt().toString();
        dto.likeCount = post.getLikeCount();

        return dto;
    }

    public void setMessage(String message){
        this.message = message;
    }
}
