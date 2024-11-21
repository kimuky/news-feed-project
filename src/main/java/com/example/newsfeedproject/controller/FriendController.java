package com.example.newsfeedproject.controller;

import com.example.newsfeedproject.dto.friend.FriendRequestDto;
import com.example.newsfeedproject.dto.friend.FriendListResponseDto;
import com.example.newsfeedproject.service.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

    @PostMapping
    public ResponseEntity<Void> requestFriend(@RequestBody FriendRequestDto requestDto, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        friendService.requestFriend(requestDto, email);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<FriendListResponseDto>> findFriendList(HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        List<FriendListResponseDto> friendList = friendService.findFriendList(email);

        return new ResponseEntity<>(friendList, HttpStatus.OK);
    }

    @PutMapping("/{friendId}")
    public ResponseEntity<Void> acceptFriendRequest(@PathVariable Long friendId, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        friendService.acceptFriendRequest(email, friendId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{friendId}/reject")
    public ResponseEntity<Void> rejectFriendRequest(@PathVariable Long friendId, HttpServletRequest servletRequest) {
        HttpSession session = servletRequest.getSession();
        String email = String.valueOf(session.getAttribute("email"));

        friendService.rejectFriendRequest(email, friendId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
