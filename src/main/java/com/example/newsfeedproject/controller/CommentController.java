package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.comment.*;
import com.example.newsfeedproject.dto.post.like.CommentLikeResponseDto;
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
                                                            @PathVariable Long postId,
                                                            HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = (String) session.getAttribute("email");

        CommentResponseDto responseDto = commentService.createComment(commentRequestDto, email, postId);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    //특정 게시물에 대한 댓글 리스트 조회
    @GetMapping
    public ResponseEntity<List<CommentAllResponseDto>> findAllComments(@PathVariable Long postId) {

        List<CommentAllResponseDto> allComments = commentService.findAllComments(postId);

        return ResponseEntity.ok().body(allComments);
    }

    //댓글 수정
    @PatchMapping("/{commentId}")
    public ResponseEntity<CommentUpdateResponseDto> updateComment(@Valid @RequestBody CommentUpdateRequestDto commentUpdateRequestDto,
                                                                  @PathVariable Long postId,
                                                                  @PathVariable Long commentId,
                                                                  HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        return ResponseEntity.ok().body(commentService.updateComment(commentUpdateRequestDto, postId, commentId, email));
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId,
                                                @PathVariable Long commentId,
                                                HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));
        commentService.deleteComment(postId, commentId, email);

        return ResponseEntity.ok().body("삭제되었습니다.");
    }

    //댓글 좋아요
    @PostMapping("/{commentId}/like")
    public ResponseEntity<CommentLikeResponseDto> insertCommentLike(@PathVariable Long commentId,
                                                                    HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        CommentLikeResponseDto commentLike = commentService.insertLike(commentId, email);
        commentLike.setMessage("좋아요를 눌렀습니다");

        return new ResponseEntity<>(commentLike, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}/like")
    public ResponseEntity<String> deleteCommentLike(@PathVariable Long commentId, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        commentService.deleteLike(commentId, email);

        return ResponseEntity.ok().body("좋아요 삭제 완료.");
    }


}
