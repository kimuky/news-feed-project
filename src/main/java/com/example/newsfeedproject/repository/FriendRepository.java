package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.Friend;
import com.example.newsfeedproject.entity.FriendId;
import com.example.newsfeedproject.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FriendRepository extends JpaRepository<Friend, FriendId> {

    // 친구 요청한 유저, 친구 요청 받은 유저를 통해 조회
    List<Friend> findFriendByToUserIdAndFromUserId(User toUserId, User fromUserId);

    // 친구 상태코드에 따른 검색
    List<Friend> findFriendByFriendRequestAndToUserId(int i, User findUser);

    // 친구 요청 찾기
    List<Friend> findFriendByToUserIdAndFromUserIdAndFriendRequest(User toUser, User fromUser, int i);

    // id와 friendRequest 를 통해 삭제
    void deleteByFromUserIdAndToUserIdAndFriendRequest(User fromUser, User toUser, int i);

    // 회원 탈퇴로 인한 친구 삭제
    void deleteByFromUserIdOrToUserIdAndFriendRequest(User findUser, User findUser1, int i);
}
