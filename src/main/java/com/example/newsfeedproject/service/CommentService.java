package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.comment.*;
import com.example.newsfeedproject.dto.post.like.CommentLikeResponseDto;
import com.example.newsfeedproject.entity.Comment;
import com.example.newsfeedproject.entity.CommentLike;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.CommentLikeRepository;
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
    private final CommentLikeRepository commentLikeRepository;

    //댓글 작성
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto requestDto, String email, Long postId) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));

        Post findPost = postRepository.findById(postId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없음"));

        Comment comment = new Comment(findPost, findUser, requestDto.getWriteComment());

        Comment saveComment = commentRepository.save(comment);
        return new CommentResponseDto(saveComment);
    }

    @Transactional
    public List<CommentAllResponseDto> findAllComments(Long postId) {
        List<Comment> commentList = commentRepository.findCommentByPostId(postId);
        List<CommentAllResponseDto> transformDtoList = commentList.stream().map(CommentAllResponseDto::new
        ).toList();
        return transformDtoList;
    }

    //댓글 수정
    @Transactional
    public CommentUpdateResponseDto updateComment(CommentRequestDto requestDto, Long postId, Long commentId, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));

        Post findPost = postRepository.findById(postId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없음"));

        Comment findComment = findCommentById(commentId);

        if (findComment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없음");
        }

        // 수정하려는 유저가 게시물 주인 혹은 댓글의 주인인 경우에만 수정 가능
        if (findUser.getId().equals(findComment.getUser().getId()) ||
                findPost.getUser().getId().equals(findUser.getId())) {
            findComment.updateComment(requestDto.getWriteComment());
            return new CommentUpdateResponseDto(findUser, findComment, postId, requestDto);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
    }

    //댓글 삭제
    @Transactional
    public void deleteComment(Long postId, Long commentId, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));

        Post findPost = postRepository.findById(postId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없음"));

        Comment findComment = findCommentById(commentId);

        if (findComment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없음");
        }

        if (findUser.getId().equals(findComment.getUser().getId()) ||
                findPost.getUser().getId().equals(findUser.getId())) {
            commentRepository.deleteById(commentId);
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "권한이 없습니다.");
        }
    }

    //댓글 좋아요 누름
    @Transactional
    public CommentLikeResponseDto insertLike(Long commentId, String email) {

        // 넘겨받은 email 통해 사용자를 찾음. 이는 user_id 컬럼에 삽임됨.
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없음"));

        boolean alreadyLiked = commentLikeRepository.existsByUserAndComment(findUser, comment);

        if (alreadyLiked) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "이미 좋아요를 눌렀습니다.");
        }

        CommentLike commentLike = new CommentLike(findUser, comment);
        comment.incrementLikeCount();

        return CommentLikeResponseDto.fromEntity(commentLikeRepository.save(commentLike));


    }

    @Transactional
    public void deleteLike(Long commentId, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없음"));

        CommentLike commentLike = commentLikeRepository.findByUserAndComment(findUser, comment).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "좋아요 하지 않음"));


        commentLikeRepository.delete(commentLike);
        comment.decrementLikeCount();
    }

    private Comment findCommentById(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(()
                -> new IllegalArgumentException("잘못된 댓글 id입니다."));
    }


}
