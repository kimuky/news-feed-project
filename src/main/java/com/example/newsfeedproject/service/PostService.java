package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.post.PostRequestDto;
import com.example.newsfeedproject.dto.post.PostResponseDto;
import com.example.newsfeedproject.dto.post.PostUpdateRequestDto;
import com.example.newsfeedproject.dto.post.PostUpdateResponseDto;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.entity.User;
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

    @Transactional
    public PostResponseDto createPost(PostRequestDto postRequestDto, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));
        Post post = new Post(findUser, postRequestDto.getTitle(), postRequestDto.getContent());

        Post savePost = postRepository.save(post);
        return PostResponseDto.fromEntity(savePost);
    }

    @Transactional
    public PostUpdateResponseDto updatePost(Long postId, PostUpdateRequestDto postUpdateRequestDto, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));
        Post post = findPostById(postId);
        post.update(findUser, postUpdateRequestDto.getTitle(), postUpdateRequestDto.getContent());

        return PostUpdateResponseDto.fromEntity(postRepository.save(post));

    }

    private Post findPostById(Long postId) {
        return postRepository.findById(postId).orElseThrow(()->new IllegalArgumentException("잘못된 게시물 id 입니다"));
    }

    @Transactional
    public void deletePost(Long postId, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));
        findPostById(postId);
        postRepository.deleteById(postId);
    }

}
