package com.example.todayerror.controller;

import com.example.todayerror.dto.UserDto.SignupRequestDto;
import com.example.todayerror.dto.UserDto.KakaoUserResponseDto;
import com.example.todayerror.security.UserDetailsImpl;
import com.example.todayerror.service.KakaoUserService;
import com.example.todayerror.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final KakaoUserService kakaoUserService;

    //회원가입
    @PostMapping("/user/signup")
    public boolean registerUser(@RequestBody SignupRequestDto signupRequestDto){
        return userService.registerUser(signupRequestDto);
    }

    // 닉네임 중복 확인
    @PostMapping("/user/idCheck")
    public boolean nicknameCheck(@RequestBody SignupRequestDto signupRequestDto) {
        return userService.nicknameCheck(signupRequestDto.getNickname());
    }

    // 카카오 소셜 로그인
    @GetMapping("/user/kakao/callback")
    public ResponseEntity<KakaoUserResponseDto> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        KakaoUserResponseDto kakaoUserResponseDto = kakaoUserService.kakaoLogin(code, response);
        return ResponseEntity.ok().body(kakaoUserResponseDto);
    }

//    @PostMapping("/isLogin")
//    public ResponseEntity<String> getUserProfile(@AuthenticationPrincipal UserDetailsImpl userDetails){
//        System.out.println(userDetails.getUsername());
//        return ResponseEntity.ok().body(userDetails.getUsername());
//    }
}