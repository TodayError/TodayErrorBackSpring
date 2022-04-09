package com.example.todayError.controller;

import com.example.todayError.dto.UserDto.SignupRequestDto;
import com.example.todayError.dto.UserDto.loginResponseDto;
import com.example.todayError.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;


    // 회원가입
    @PostMapping("/signup")
    public String resisterUser(@RequestBody SignupRequestDto signupRequestDto) {
        userService.resisterUser(signupRequestDto);
        return "회원가입 성공";
    }

    // 아이디 중복 확인
    @PostMapping("/idCheck")
    public boolean nicknameCheck(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.nicknameCheck(signupRequestDto.getNickname());
    }

//    // 로그인
//    @PostMapping("/login")
//    public loginResponseDto loginUser(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        User user = userDetails.getUser();
//        return new loginResponseDto(user.getId(), user.getNickname());
//    }
}
