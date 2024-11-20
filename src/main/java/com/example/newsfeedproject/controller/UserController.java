package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.user.RegisterUserRequestDto;
import com.example.newsfeedproject.dto.user.RegisterUserResponseDto;
import com.example.newsfeedproject.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("signup")
    public ResponseEntity<RegisterUserResponseDto> registerUser (@Valid @RequestBody RegisterUserRequestDto requestDto) {
        RegisterUserResponseDto responseDto = userService.registerUser(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }
}
