package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.Post;
import com.example.newsfeedproject.entity.PostLike;
import com.example.newsfeedproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
    boolean existsByUserAndPost(User user, Post post);
}
