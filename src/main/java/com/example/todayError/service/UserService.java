package com.example.todayError.service;

import com.example.todayError.domain.User;
import com.example.todayError.dto.UserDto.SignupRequestDto;
import com.example.todayError.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public void resisterUser(SignupRequestDto signupRequestDto) {

        String nickname = signupRequestDto.getNickname();

        Optional<User> found = userRepository.findByNickname(nickname);
        if (found.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자 ID 가 존재합니다.");
        }

        // password 암호화
        String enPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        User user = new User(signupRequestDto, enPassword);
        userRepository.save(user);
    }

    public boolean nicknameCheck(String nickname) {
        return userRepository.existsByNickname(nickname);
    }
}
