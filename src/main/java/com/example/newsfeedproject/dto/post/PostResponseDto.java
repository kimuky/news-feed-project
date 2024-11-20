package com.example.newsfeedproject.dto.post;

import com.example.newsfeedproject.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String content;
    private String writerName; //작성자 이름
    private String createdDate; //작성일

    //Post Entity -> Dto 변환
    public static PostResponseDto fromEntity(Post post){
        PostResponseDto dto = new PostResponseDto();

        dto.id = post.getId();
        dto.title = post.getTitle();
        dto.content = post.getContent();
        dto.writerName = post.getUser().getName(); //UserEntity에서 이름 가져오기
        dto.createdDate = post.getCreatedAt().toString(); //작성일을 문자열로 변환

        return dto;
    }
}