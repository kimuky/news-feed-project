package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    // User 객체 아이디 기준
    List<Friend> findByUser_Id(Long userId);

    @Query(
            value = "SELECT  u.id, u.name FROM friend f join user u on f.from_user_id = u.id where to_user_id = :user_id", nativeQuery = true
    )
    List<Object[]> fd(@Param(value="user_id") Long user_id);

}
