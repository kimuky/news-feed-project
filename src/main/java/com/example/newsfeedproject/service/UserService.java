package com.example.newsfeedproject.service;

import com.example.newsfeedproject.config.PasswordEncoder;
import com.example.newsfeedproject.dto.user.*;
import com.example.newsfeedproject.entity.User;
import com.example.newsfeedproject.repository.FriendRepository;
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
    private final FriendRepository friendRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public RegisterUserResponseDto registerUser(RegisterUserRequestDto requestDto) {
        // 비밀번호 암호화
        Optional<User> findUser = userRepository.findUserByEmail(requestDto.getEmail());

        // 아메일이 중복일 시, 중복 예외 처리
        if (findUser.isPresent()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "중복된 아이디입니다.");
        }

        User user = new User(requestDto, passwordEncoder.encode(requestDto.getPassword()));
        User saveUser = userRepository.save(user);

        return new RegisterUserResponseDto(saveUser);
    }

    public LoginUserResponseDto login(LoginUserRequestDto requestDto) {
        User findUser = findUserByEmailOrElseThrow(requestDto.getEmail());

        if (findUser.getActivated() == 0) {
            throw  new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        // 패스워드가 일치하지 않을 시, 예외처리
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

        // 인가
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

            if (passwordEncoder.matches(requestDto.getPassword(), findUser.getPassword())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "동일한 패스워드 입니다.");
            }

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

        // 유저 소프트딜리트
        findUser.softDelete();

        friendRepository.deleteByFromUserIdOrToUserId(findUser, findUser);
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
