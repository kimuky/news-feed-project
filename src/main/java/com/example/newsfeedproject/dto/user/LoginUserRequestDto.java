package com.example.newsfeedproject.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class LoginUserRequestDto {

    @NotBlank(message = "이메일을 꼭 입력해주세요")
    @Email (message = "이메일 형식을 맞춰주세요")
    private final String email;

    @NotBlank(message = "이메일을 꼭 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "영어, 숫자, 특수문자 포함 8~20글자까지 입력해주세요")
    private final String password;

    public LoginUserRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
