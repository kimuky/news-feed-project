package com.example.newsfeedproject.dto.comment;


import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {

    @NotBlank(message = "내용을 입력해주세요.")
    private final String writeComment;

    @JsonCreator
    public CommentUpdateRequestDto(String writeComment) {
        this.writeComment = writeComment;
    }
}
