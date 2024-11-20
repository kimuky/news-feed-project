package com.example.newsfeedproject.controller;


import com.example.newsfeedproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostController {

    private final PostRepository postRepository;
    private final UserService userService;

    //test2


}
