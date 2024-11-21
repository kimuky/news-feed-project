package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.post.PostRequestDto;
import com.example.newsfeedproject.dto.post.PostResponseDto;
import com.example.newsfeedproject.dto.post.PostUpdateRequestDto;
import com.example.newsfeedproject.dto.post.PostUpdateResponseDto;
import com.example.newsfeedproject.dto.post.like.PostLikeResponseDto;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.entity.PostLike;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.LikeRepository;
import com.example.newsfeedproject.repository.PostRepository;
import com.example.newsfeedproject.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public Page<PostResponseDto> getAllPosts(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(PostResponseDto::fromEntity);
    }

    public Page<PostResponseDto> getPostsByUser(Long userId, Pageable pageable) {
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(PostResponseDto::fromEntity);
    }

    public PostResponseDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        return PostResponseDto.fromEntity(post);
    }

    //게시물 작성
    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));
        Post post = new Post(findUser, postRequestDto.getTitle(), postRequestDto.getContent());

        Post savePost = postRepository.save(post);
        return PostResponseDto.fromEntity(savePost);
    }

    //게시물 수정
    @Transactional
    public PostUpdateResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));

        Post post = findPostById(postId);

        validatePostOwner(post, findUser);

        post.update(findUser, postUpdateRequestDto.getTitle(), postUpdateRequestDto.getContent());

        return PostUpdateResponseDto.fromEntity(postRepository.save(post));

    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("잘못된 게시물 id 입니다"));
    }

    //게시물 작성자 확인 로직
    private void validatePostOwner(Post post, User user) {
        if(!post.getUser().getId().equals(user.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성자만 게시글을 삭제할 수 있습니다");
        }
    }
    //게시물 삭제
    @Transactional
    public void deletePost(Long postId, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));

        Post findPost = findPostById(postId);
        findPost.getUser().getId();

        validatePostOwner(findPost, findUser);

        postRepository.deleteById(postId);
    }

    @Transactional
    public PostLikeResponseDto insertLike(Long postId, String email) {
        // 넘겨받은 email을 통해 사용자를 찾음. 이는 user_id 컬럼에 삽임됨.
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));

        // 넘겨받은 postId를 통해 게시물을 찾음, 이는 post_id 컬럼에 삽입됨.
        //Post 인지 PostLike 인지?
        Post post = postRepository.findById(postId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없음"));

        // user_id, post_id를 테이블에 업데이트 함.
        PostLike postLike = new PostLike(findUser, post);



        return PostLikeResponseDto.fromEntity(likeRepository.save(postLike));
    }


}
