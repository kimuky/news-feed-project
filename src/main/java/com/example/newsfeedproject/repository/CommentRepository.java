package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.Comment;
import com.example.newsfeedproject.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPost(Post post);
}
