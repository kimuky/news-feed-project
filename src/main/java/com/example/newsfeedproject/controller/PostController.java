package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.post.PostResponseDto;
import com.example.newsfeedproject.service.PostService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.SessionStatus;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    //전체 게시물 조회
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(Pageable pageable, HttpSession session){
        validateSession(session);

        Page<PostResponseDto> posts = postService.getAllPosts(pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //특정 사용자의 게시물 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostResponseDto>> getPostsByUser(
            @PathVariable Long userId, Pageable pageable, HttpSession session
    ){
        validateSession(session);
        Page<PostResponseDto> posts = postService.getPostsByUser(userId, pageable);

        ResponseEntity<Page<PostResponseDto>> ResponseEntity;
        if(posts.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //단일 게시물 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(@PathVariable Long postId, HttpSession session){
        validateSession(session);
        try{
            PostResponseDto post = postService.getPostById(postId);
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //세션 검사
    private void validateSession(HttpSession session){
        if(session == null || session.getAttribute("email") == null){
            throw new RuntimeException("로그인을 해주세요.");
        }
    }
}
