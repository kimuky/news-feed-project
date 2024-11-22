package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.comment.CommentRequestDto;
import com.example.newsfeedproject.dto.comment.CommentResponseDto;
import com.example.newsfeedproject.dto.comment.CommentUpdateRequestDto;
import com.example.newsfeedproject.dto.comment.CommentUpdateResponseDto;
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
import java.util.Optional;


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
        return CommentResponseDto.fromEntity(saveComment);
    }

    public List<CommentResponseDto> findAllComments(Long postId) {

        List<Comment> commentList = commentRepository.findCommentByPostId(postId);
        List<CommentResponseDto> list = commentList.stream().map(Comment -> {
                    Post post = Comment.getPost();
                    User user = Comment.getUser();
                    return new CommentResponseDto(Comment.getId(), user.getId(), post.getId(), user.getName(), Comment.getWriteComment(), Comment.getLikeCount());
                }
        ).toList();
        return list;
    }

    //댓글 수정

    @Transactional
    public CommentUpdateResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto,
                                                  String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));
        Comment comment = findCommentById(commentId);
//        comment.update(commentUpdateRequestDto.getWriteComment(), findUser);

        return CommentUpdateResponseDto.fromEntity(commentRepository.save(comment));
    }
    //댓글 삭제

    @Transactional
    public void deleteComment(Long commentId, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));
        Comment findComment = findCommentById(commentId);
        commentRepository.deleteById(commentId);
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
