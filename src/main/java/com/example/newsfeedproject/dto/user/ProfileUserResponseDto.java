package com.example.newsfeedproject.dto.user;

import com.example.newsfeedproject.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ProfileUserResponseDto {

    private final Long id;

    private final String email;

    private final String name;

    private final String age;

    private final String introduce;

    private final LocalDateTime createDate;

    private final LocalDateTime updateDate;

    public ProfileUserResponseDto(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.name = user.getName();
        this.age = user.getAge() == 0 ? "알 수 없음" : String.valueOf(user.getAge());
        this.introduce = user.getIntroduce() == null ? "없음" : user.getIntroduce();
        this.createDate = user.getCreatedAt();
        this.updateDate = user.getUpdatedAt();
    }
}
