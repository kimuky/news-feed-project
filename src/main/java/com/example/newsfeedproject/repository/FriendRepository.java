package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.dto.friend.FriendListResponseDto;
import com.example.newsfeedproject.entity.Friend;
import com.example.newsfeedproject.entity.FriendId;
import com.example.newsfeedproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {

    // 친구 요청한 유저, 친구 요청 받은 유저를 통해 조회
    List<Friend> findFriendByToUserIdAndFromUserId(User toUserId, User fromUserId);

    // 친구 요청을 한 아이디와 이름을 반환
    @Query(value = "select new  com.example.newsfeedproject.dto.friend.FriendListResponseDto(u.id, u.name) from friend f  join f.fromUserId u where f.toUserId = :user_id")
    List<FriendListResponseDto> findFriendList(@Param(value = "user_id") User user_id);

    void deleteByFromUserIdAndToUserId(User fromUser, User toUser);


    // TODO 공부
    // List<Friend> findByUser_Id(Long userId);

}
