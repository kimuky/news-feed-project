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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    //전체 게시물 조회
    public Page<PostResponseDto> getAllPosts(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);

        if(posts.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"게시물이 존재하지 않습니다.");
        }
        return posts.map(PostResponseDto::fromEntity);
    }

    //특정 사용자의 게시물 조회
    public Page<PostResponseDto> getPostsByUser(Long userId, Pageable pageable) {
        Page<Post> posts = postRepository.findByUserIdOrderByCreatedAtDesc(userId,pageable);

        if(posts.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 사용자의 게시물이 존재하지 않습니다.");
        }
        return posts.map(PostResponseDto::fromEntity);
    }

    //단일 게시물 조회
    public PostResponseDto getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"게시물을 찾을 수 없습니다."));

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
        return postRepository.findById(postId).orElseThrow(() -> new IllegalArgumentException("잘못된 게시물 id 입니다"));
    }

    @Transactional
    public void deletePost(Long postId, String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));
        Post findPost = findPostById(postId);
        findPost.getUser().getId();
        if (!findPost.getUser().getId().equals(findUser.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "작성자만 게시글을 삭제할 수 있습니다");
        }
        postRepository.deleteById(postId);
    }

}
