package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.friend.FriendRequestDto;
import com.example.newsfeedproject.service.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    @PostMapping
    public ResponseEntity<Void> requestFriend (@RequestBody FriendRequestDto requestDto, HttpServletRequest servletRequest) {

        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        friendService.requestFriend(requestDto, email);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
