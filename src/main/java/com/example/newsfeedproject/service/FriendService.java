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
        User fromUser = userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없음"));
        User toUser = userRepository.findUserByEmail(requestDto.getUserEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없음"));

        if (fromUser.getId().equals(toUser.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자신에게 친구 요청을 할 수 없습니다.");
        }

        List<Friend> findRelation = friendRepository.findFriendByToUserIdAndFromUserId(toUser, fromUser);

        if (!findRelation.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "한번 더 친구 요청을 할 수 없습니다. ");
        }

        // 이미 친구 요청한 상대에게 친추를 한경우
        List<Friend> alreadyRequest = friendRepository.findFriendByToUserIdAndFromUserId(fromUser, toUser);
        if (!alreadyRequest.isEmpty()) {
            Friend fromFriend = new Friend(toUser, fromUser, 1);
            friendRepository.save(fromFriend);
            Friend toFriend = new Friend(fromUser, toUser, 1);
            friendRepository.save(toFriend);
        } else {
            Friend friend = new Friend(fromUser, toUser);
            friendRepository.save(friend);
        }
    }

    @Transactional
    public List<FriendListResponseDto> findFriendList(String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없음"));

        // TODO 공부
        // List<Friend> friend2s = friendRepository.findFriend2ByToUserId(findUser);
        // List<User> fromUsers = friend2s.stream().map(f -> f.getFromUserId()).toList();

        return friendRepository.findFriendList(findUser);
    }

    @Transactional
    public void acceptFriendRequest(String email, Long friendId) {
        User fromUser = userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없음"));
        User toUser = userRepository.findById(friendId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없음"));

        if (fromUser.getId().equals(toUser.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자신에게 친구 수락을 할 수 없습니다.");
        }

        // 친구 요청한 사람 레코드 갱신
        Friend fromFriend = new Friend(toUser, fromUser, 1);
        friendRepository.save(fromFriend);

        // 친구 요청 받은 사람 레코드 추가
        Friend toFriend = new Friend(fromUser, toUser, 1);
        friendRepository.save(toFriend);
    }

    @Transactional
    public void rejectFriendRequest(String email, Long friendId) {
        // 로그인 한 사용자
        User toUser = userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없음"));

        // 친구요청한 사용자
        User fromUser = userRepository.findById(friendId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없음"));

        if (fromUser.getId().equals(toUser.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "자신에게 친구 거절을 할 수 없습니다.");
        }

        friendRepository.deleteByFromUserIdAndToUserId(fromUser, toUser);
        friendRepository.deleteByFromUserIdAndToUserId(toUser, fromUser);
    }
}
