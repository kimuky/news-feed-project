package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<PostLike, Long> {
}
