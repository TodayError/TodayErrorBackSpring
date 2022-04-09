package com.example.todayError.controller;

import com.example.todayError.dto.UserDto.SignupRequestDto;
import com.example.todayError.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/user/signup")
    public void resisterUser(@RequestBody SignupRequestDto signupRequestDto) {
        userService.resisterUser(signupRequestDto);
    }

    // 아이디 중복 확인
    @PostMapping("/user/idCheck")
    public boolean nicknameCheck(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.nicknameCheck(signupRequestDto.getNickname());
    }
}