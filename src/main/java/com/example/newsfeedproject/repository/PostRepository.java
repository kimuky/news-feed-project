package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    //특정 사용자의 게시물 조회, 생성 시간을 기준으로 정렬
    Page<Post> findByUserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);
}
