package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.dto.friend.FriendListResponseDto;
import com.example.newsfeedproject.entity.Friend;
import com.example.newsfeedproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    // TODO 공부
    // List<Friend> findByUser_Id(Long userId);
     List<Friend> findFriendByToUserIdAndFromUserId(User toUserId, User fromUserId);

    @Query(value = "select new  com.example.newsfeedproject.dto.friend.FriendListResponseDto(u.id, u.name) from friend f  join f.fromUserId u where f.toUserId = :user_id")
    List<FriendListResponseDto> findFriendList(@Param(value = "user_id") User user_id);
}
