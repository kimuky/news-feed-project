package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {
}
