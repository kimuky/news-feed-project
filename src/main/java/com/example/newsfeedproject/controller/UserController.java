package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.user.*;
import com.example.newsfeedproject.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<RegisterUserResponseDto> registerUser(@Valid @RequestBody RegisterUserRequestDto requestDto) {
        RegisterUserResponseDto responseDto = userService.registerUser(requestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginUserResponseDto> login(@Valid @RequestBody LoginUserRequestDto requestDto, HttpServletRequest servletRequest) {
        LoginUserResponseDto responseDto = userService.login(requestDto);

        HttpSession session = servletRequest.getSession();
        session.setAttribute("email", requestDto.getEmail());

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileUserResponseDto> findByUserId(@PathVariable Long userId) {

        ProfileUserResponseDto responseDto = userService.findByUserId(userId);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<ProfileUserResponseDto> updateProfile(@PathVariable Long userId, @RequestBody UpdateUserRequestDto requestDto, HttpServletRequest servletRequest) {

        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        ProfileUserResponseDto responseDto = userService.updateProfile(userId, requestDto, email);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
