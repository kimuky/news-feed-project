package com.example.newsfeedproject.controller;


import com.example.newsfeedproject.dto.post.PostRequestDto;
import com.example.newsfeedproject.dto.post.PostResponseDto;
import com.example.newsfeedproject.service.PostService;
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
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;


    //작성
    @PostMapping()
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRequestDto, email));
    }


    //수정

    //삭제


}
