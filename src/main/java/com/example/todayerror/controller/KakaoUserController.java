package com.example.todayerror.controller;

import com.example.todayerror.service.KakaoUserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

//@Controller
//@RequiredArgsConstructor
//public class KakaoUserController {
//
//    private final KakaoUserService kakaoUserService;
//    private final String AUTH_HEADER = "Authorization";
//
//    @GetMapping("/user/kakao/callback")
//    public void kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
//        String token = kakaoUserService.kakaoLogin(code);
//        response.addHeader(AUTH_HEADER, token);
//    }
//}