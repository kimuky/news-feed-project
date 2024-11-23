package com.example.newsfeedproject.dto.user;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UpdateUserRequestDto {

    private final String name;

    private final int age;

    private final String introduce;

    @NotBlank(message = "패스워드를 꼭 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "영어, 숫자, 특수문자 포함 8~20글자까지 입력해주세요")
    private final String originalPassword;

    private final String changePassword;

    public UpdateUserRequestDto(String name, int age, String introduce, String originalPassword, String changePassword) {
        this.name = name;
        this.age = age;
        this.introduce = introduce;
        this.originalPassword = originalPassword;
        this.changePassword = changePassword;
    }
}
