package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.Friend;
import com.example.newsfeedproject.entity.FriendId;
import com.example.newsfeedproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {

    // 친구 요청한 유저, 친구 요청 받은 유저를 통해 조회
    List<Friend> findFriendByToUserIdAndFromUserId(User toUserId, User fromUserId);

    // 친구레코드 삭제
    void deleteByFromUserIdAndToUserId(User fromUser, User toUser);

    // 친구 상태코드에 따른 검색
    List<Friend> findFriendByFriendRequestAndToUserId(int i, User findUser);

    // 회원 탈퇴에 따른 친구 삭제
    void deleteByFromUserIdOrToUserId(User fromUser, User toUser);
}
