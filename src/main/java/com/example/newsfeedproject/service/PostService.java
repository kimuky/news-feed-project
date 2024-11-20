package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.post.PostResponseDto;
import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<PostResponseDto> getAllPosts(Pageable pageable){
        return postRepository.findAll(pageable)
                .map(PostResponseDto::fromEntity);
    }

    public Page<PostResponseDto> getPostsByUser(Long userId, Pageable pageable){
        return postRepository.findByUserIdOrderByCreatedAtDesc(userId, pageable)
                .map(PostResponseDto::fromEntity);
    }

    public PostResponseDto getPostById(Long postId){
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다."));
        return PostResponseDto.fromEntity(post);
    }

}
