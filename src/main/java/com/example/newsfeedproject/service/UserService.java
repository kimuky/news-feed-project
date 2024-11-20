package com.example.newsfeedproject.service;

import com.example.newsfeedproject.config.PasswordEncoder;
import com.example.newsfeedproject.dto.user.RegisterUserRequestDto;
import com.example.newsfeedproject.dto.user.RegisterUserResponseDto;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterUserResponseDto registerUser(RegisterUserRequestDto requestDto) {
        User user = new User(requestDto, passwordEncoder.encode(requestDto.getPassword()));

        Optional<User> findUser = findByIdOrElseThrow(user.getEmail());

        if (findUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "중복된 아이디입니다.");
        }

        User saveUser = userRepository.save(user);

        return new RegisterUserResponseDto(saveUser);
    }

    private Optional<User> findByIdOrElseThrow(String email) {
        return userRepository.findUserByEmail(email);
    }
}
