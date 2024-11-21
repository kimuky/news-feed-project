package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.friend.FriendListResponseDto;
import com.example.newsfeedproject.dto.friend.FriendRequestDto;
import com.example.newsfeedproject.entity.Friend;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.FriendRepository;
import com.example.newsfeedproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendService {

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Transactional
    public void requestFriend(FriendRequestDto requestDto, String email) {
        // 친구 요청한 유저
        User fromUser = findUserByEmailOrElseThrow(email);
        // 요청 받은 유저
        User toUser = findUserByEmailOrElseThrow(requestDto.getUserEmail());

        // 자기 자신에게 친구 요청 시, 예외 처리
        if (fromUser.getId().equals(toUser.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자신에게 친구 요청을 할 수 없습니다.");
        }

        // 요청 받은 유저, 요청한 유저 id 기준으로 조회
        List<Friend> findRelation = friendRepository.findFriendByToUserIdAndFromUserId(toUser, fromUser);

        if (!findRelation.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "한번 더 친구 요청을 할 수 없습니다. ");
        }

        // 상대가 이미 나에게 신청을 했는지 확인
        List<Friend> alreadyRequest = friendRepository.findFriendByToUserIdAndFromUserId(fromUser, toUser);

        // 이미 있으면 친구로 등록
        // 없으면 요청만
        if (!alreadyRequest.isEmpty()) {
            saveFriendRelation(fromUser,toUser);
        } else {
            Friend friend = new Friend(fromUser, toUser);
            friendRepository.save(friend);
        }
    }

    @Transactional
    public List<FriendListResponseDto> findFriendList(String email) {
        User findUser = findUserByEmailOrElseThrow(email);

        return friendRepository.findFriendList(findUser);
    }

    @Transactional
    public void acceptFriendRequest(String email, Long friendId) {
        User fromUser = findUserByEmailOrElseThrow(email);
        User toUser = findByIdOrElseThrow(friendId);

        if (fromUser.getId().equals(toUser.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자신에게 친구 수락을 할 수 없습니다.");
        }

        saveFriendRelation(fromUser,toUser);
    }

    @Transactional
    public void rejectFriendRequest(String email, Long friendId) {
        // 로그인 한 사용자
        User toUser = findUserByEmailOrElseThrow(email);

        // 친구요청한 사용자
        User fromUser = findByIdOrElseThrow(friendId);

        if (fromUser.getId().equals(toUser.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자신에게 친구 거절을 할 수 없습니다.");
        }

        friendRepository.deleteByFromUserIdAndToUserId(fromUser, toUser);
        friendRepository.deleteByFromUserIdAndToUserId(toUser, fromUser);
    }

    private void saveFriendRelation(User fromUser, User toUser) {
        // 친구 요청한 사람 레코드 갱신
        Friend fromFriend = new Friend(toUser, fromUser, 1);
        friendRepository.save(fromFriend);

        // 친구 요청 받은 사람 레코드 추가
        Friend toFriend = new Friend(fromUser, toUser, 1);
        friendRepository.save(toFriend);
    }

    private User findUserByEmailOrElseThrow(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없음"));
    }

    private User findByIdOrElseThrow(Long friendId) {
        return userRepository.findById(friendId).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없음"));
    }
}
