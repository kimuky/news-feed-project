package com.example.newsfeedproject.controller;


import com.example.newsfeedproject.dto.post.PostRequestDto;
import com.example.newsfeedproject.dto.post.PostResponseDto;
import com.example.newsfeedproject.dto.post.PostUpdateRequestDto;
import com.example.newsfeedproject.dto.post.PostUpdateResponseDto;
import com.example.newsfeedproject.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;


    //작성
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostRequestDto postRequestDto, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(postRequestDto, email));
    }


    //수정
    @PatchMapping("/{id}")
    public ResponseEntity<PostUpdateResponseDto> updatePost(@RequestBody PostUpdateRequestDto postUpdateRequestDto, @PathVariable Long id, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        return ResponseEntity.ok().body(postService.updatePost(id, postUpdateRequestDto, email));
    }

    //삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));
        postService.deletePost(id, email);
        return ResponseEntity.ok().body("정상적으로 삭제되었습니다.");
    }


}
