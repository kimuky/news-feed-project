package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.post.PostRequestDto;
import com.example.newsfeedproject.dto.post.PostResponseDto;
import com.example.newsfeedproject.dto.post.PostUpdateRequestDto;
import com.example.newsfeedproject.dto.post.PostUpdateResponseDto;
import com.example.newsfeedproject.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    //전체 게시물 조회
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> getAllPosts(
            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable,
            HttpSession session

    ){
        validateSession(session);
        Page<PostResponseDto> posts = postService.getAllPosts(pageable);
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //특정 사용자의 게시물 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<PostResponseDto>> getPostsByUser(
            @PathVariable Long userId,
            @PageableDefault(
                    size = 10,
                    page = 0,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC
            )
            Pageable pageable,
            HttpSession session
    ) {
        validateSession(session);
        Page<PostResponseDto> posts = postService.getPostsByUser(userId, pageable);

        return new ResponseEntity<>(posts, HttpStatus.OK);
    }

    //단일 게시물 조회
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponseDto> getPostById(
            @PathVariable Long postId,
            HttpSession session){
        validateSession(session);
        PostResponseDto post = postService.getPostById(postId);
      
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    //세션 검사
    private void validateSession(HttpSession session) {
        if (session == null || session.getAttribute("email") == null) {
            throw new IllegalStateException("로그인을 해주세요.");
        }
    }
}