package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
