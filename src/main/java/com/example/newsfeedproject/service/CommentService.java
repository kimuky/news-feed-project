package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.comment.CommentRequestDto;
import com.example.newsfeedproject.dto.comment.CommentResponseDto;
import com.example.newsfeedproject.dto.comment.CommentUpdateRequestDto;
import com.example.newsfeedproject.dto.comment.CommentUpdateResponseDto;
import com.example.newsfeedproject.entity.Comment;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.CommentRepository;
import com.example.newsfeedproject.repository.PostRepository;
import com.example.newsfeedproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final PostRepository postRepository;

    private final UserRepository userRepository;

    private final CommentRepository commentRepository;

    //댓글 작성
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));
        Comment comment = new Comment(commentRequestDto.getWriteComment(), findUser);

        Comment saveComment = commentRepository.save(comment);
        return CommentResponseDto.fromEntity(saveComment);
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()
                -> new IllegalArgumentException("잘못된 댓글 id입니다."));
    }

    public List<CommentResponseDto> findAllComments() {
        List<Comment> comments = commentRepository.findAll();

        return comments
                .stream()
                .map(CommentResponseDto::toDto)
                .toList();
    }

    //댓글 수정
    @Transactional
    public CommentUpdateResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto,
                                                  String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));
        Comment comment = findCommentById(commentId);
        comment.update(commentUpdateRequestDto.getWriteComment(), findUser);

        return CommentUpdateResponseDto.fromEntity(commentRepository.save(comment));
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long commentId, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));
        Comment findComment = findCommentById(commentId);
        findComment.getUser().getId();
        commentRepository.deleteById(commentId);
    }
}
