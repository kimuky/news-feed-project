package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.friend.FriendDeleteRequestDto;
import com.example.newsfeedproject.dto.friend.FriendListResponseDto;
import com.example.newsfeedproject.dto.friend.FriendPasswordRequestDto;
import com.example.newsfeedproject.dto.friend.FriendRequestDto;
import com.example.newsfeedproject.service.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/friends")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;

    // 친구 요청
    @PostMapping
    public ResponseEntity<Void> requestFriend(@Valid @RequestBody FriendRequestDto requestDto,
                                              HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        friendService.requestFriend(requestDto, email);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    // friendRequest 따른 친구 목록 조회 0은 요청한 목록 리스트, 1은 친구리스트 조회
    @GetMapping
    public ResponseEntity<List<FriendListResponseDto>> findFriendListByFriendRequest(@RequestParam(value = "friendRequest", defaultValue = "0")
                                                                                     int friendRequest,
                                                                                     HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        List<FriendListResponseDto> friendList = friendService.findFriendListByFriendRequest(email, friendRequest);

        return new ResponseEntity<>(friendList, HttpStatus.OK);
    }

    // 친구 수락
    @PostMapping("/{friendId}")
    public ResponseEntity<Void> acceptFriendRequest(@PathVariable Long friendId,
                                                    HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        friendService.acceptFriendRequest(email, friendId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 친구 삭제
    @DeleteMapping
    public ResponseEntity<Void> removeFriendRequest(@Valid @RequestBody FriendDeleteRequestDto requestDto,
                                                    HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        friendService.removeFriendRequest(requestDto, email);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 친구 거절
    @DeleteMapping("/{friendId}")
    public ResponseEntity<Void> rejectFriendRequest(@PathVariable Long friendId,
                                                    @Valid @RequestBody FriendPasswordRequestDto requestDto,
                                                    HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        friendService.rejectFriendRequest(requestDto, email, friendId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
