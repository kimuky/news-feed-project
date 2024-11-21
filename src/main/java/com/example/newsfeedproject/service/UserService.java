package com.example.newsfeedproject.service;

import com.example.newsfeedproject.config.PasswordEncoder;
import com.example.newsfeedproject.dto.user.*;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterUserResponseDto registerUser(RegisterUserRequestDto requestDto) {
        User user = new User(requestDto, passwordEncoder.encode(requestDto.getPassword()));

        Optional<User> findUser = userRepository.findUserByEmail(requestDto.getEmail());

        if (findUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "중복된 아이디입니다.");
        }

        User saveUser = userRepository.save(user);

        return new RegisterUserResponseDto(saveUser);
    }

    public LoginUserResponseDto login(LoginUserRequestDto requestDto) {
        User findUser = findUserByEmailOrElseThrow(requestDto.getEmail());

        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀번호 틀림");
        }

        return new LoginUserResponseDto(findUser);
    }

    public ProfileUserResponseDto findByUserId(Long userId) {
        User findUser = findByUserIdOrElseThrow(userId);

        return new ProfileUserResponseDto(findUser);
    }

    @Transactional
    public ProfileUserResponseDto updateProfile(Long userId, UpdateUserRequestDto requestDto, String email) {
        User findUser = findByUserIdOrElseThrow(userId);

        if (!findUser.getEmail().equals(email)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 프로필 유저가 아님");
        }

        if (requestDto.getName() != null) {
            findUser.updateName(requestDto.getName());
        }

        if (requestDto.getAge() != 0) {
            findUser.updateAge(requestDto.getAge());
        }

        if (requestDto.getIntroduce() != null) {
            findUser.updateIntroduce(requestDto.getIntroduce());
        }

        if (requestDto.getPassword() != null) {
            if(!Pattern.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,20}$", requestDto.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "영어, 숫자, 특수문자 포함 8~20글자까지 입력해주세요");
            }
            findUser.updatePassword(passwordEncoder.encode(requestDto.getPassword()));
        }
        return new ProfileUserResponseDto(findUser);
    }

    @Transactional
    public void softDeleteProfile(Long userId, PasswordUserRequestDto requestDto, String email) {
        User findUser = findByUserIdOrElseThrow(userId);

        if (!findUser.getEmail().equals(email) ) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "해당 프로필 유저가 아님");
        }

        if (!passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "패스워드가 틀림");
        }
        findUser.softDelete();
    }

    private User findUserByEmailOrElseThrow(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없음"));
    }

    private User findByUserIdOrElseThrow(Long id) {
        return userRepository.findById(id).orElseThrow(()
                -> new ResponseStatusException(HttpStatus.NOT_FOUND, "아이디를 찾을 수 없음"));
    }
}
