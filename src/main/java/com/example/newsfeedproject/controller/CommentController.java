package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.comment.CommentRequestDto;
import com.example.newsfeedproject.dto.comment.CommentResponseDto;
import com.example.newsfeedproject.dto.comment.CommentUpdateRequestDto;
import com.example.newsfeedproject.dto.comment.CommentUpdateResponseDto;
import com.example.newsfeedproject.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {

    private final CommentService commentService;

    //댓글 작성
    @PostMapping
    public ResponseEntity<CommentResponseDto> createComment(@Valid @RequestBody CommentRequestDto commentRequestDto,
                                                            HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = (String) session.getAttribute("email");

        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(commentRequestDto, email));
    }

    //댓글 조회
    @GetMapping
    public ResponseEntity<List<CommentResponseDto>> findAllComments() {
        return ResponseEntity.ok().body(commentService.findAllComments());
    }

    //댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentUpdateResponseDto> updateComment(@Valid @RequestBody CommentUpdateRequestDto commentUpdateRequestDto,
                                                                  @PathVariable Long commentId, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        return ResponseEntity.ok().body(commentService.updateComment(commentId, commentUpdateRequestDto, email));
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long commentId, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));
        commentService.deleteComment(commentId, email);

        return ResponseEntity.ok().body("삭제되었습니다.");
    }


}
