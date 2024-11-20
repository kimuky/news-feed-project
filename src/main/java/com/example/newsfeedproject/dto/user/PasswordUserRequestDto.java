package com.example.newsfeedproject.dto.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class PasswordUserRequestDto {

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", message = "영어, 숫자, 특수문자 포함 8~20글자까지 입력해주세요")
    private final String password;

    @JsonCreator
    public PasswordUserRequestDto(String password) {
        this.password = password;
    }
}
