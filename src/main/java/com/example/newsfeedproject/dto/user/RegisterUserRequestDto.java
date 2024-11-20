package com.example.newsfeedproject.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class RegisterUserRequestDto {

    @NotBlank(message = "이메일을 꼭 입력해주세요")
    @Email (message = "이메일 형식을 맞춰주세요")
    private final String email;

    @NotBlank(message = "이메일을 꼭 입력해주세요")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "영어, 숫자, 특수문자 포함 8~20글자까지 입력해주세요")
    private final String password;

    @NotBlank(message = "이름은 꼭 입력해주세요")
    private final String name;

    private final int age;

    public RegisterUserRequestDto(String email, String password, String name, int age) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.age = age;
    }
}
