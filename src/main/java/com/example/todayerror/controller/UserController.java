package com.example.todayerror.controller;

import com.example.todayerror.dto.UserDto.SignupRequestDto;
import com.example.todayerror.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping("/user/signup")
    public void registerUser(@RequestBody SignupRequestDto signupRequestDto){
        userService.registerUser(signupRequestDto);
    }

    // 닉네임 중복 확인
    @PostMapping("/user/idCheck")
    public boolean nicknameCheck(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.nicknameCheck(signupRequestDto.getNickname());
    }
}