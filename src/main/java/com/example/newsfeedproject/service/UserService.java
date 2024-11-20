package com.example.newsfeedproject.service;

import com.example.newsfeedproject.dto.user.RegisterUserRequestDto;
import com.example.newsfeedproject.dto.user.RegisterUserResponseDto;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public RegisterUserResponseDto registerUser(RegisterUserRequestDto requestDto) {
        User user = new User(requestDto);
        User saveUser = userRepository.save(user);

        return new RegisterUserResponseDto(saveUser);
    }
}
