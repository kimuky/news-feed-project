package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.friend.FriendRequestDto;
import com.example.newsfeedproject.dto.friend.FriendResponseDto;
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
        User toUser  = userRepository.findUserByEmail(requestDto.getUserEmail()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없음"));

        Friend friend = new Friend(fromUser.getId(), toUser);

        friendRepository.save(friend);
    }

    @Transactional
    public List<FriendResponseDto> findAllByEmail(String email) {
        User findUser = userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없음"));

        // List<Friend> friendByToUserId = friendRepository.findByUser_Id(findUser.getId());
        List<Object[]> byUser = friendRepository.fd(findUser.getId());

        return byUser.stream().map(result -> new FriendResponseDto((Long)result[0], (String)result[1])).toList();
    }
}
