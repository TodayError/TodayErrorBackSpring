package com.example.todayerror.validator;

import com.example.todayerror.dto.UserDto.SignupRequestDto;

import java.util.IllformedLocaleException;
import java.util.regex.Pattern;

public class UserInfoValidator {
    public static void validateUserRegister(SignupRequestDto signupRequestDto) {
        String nickname = signupRequestDto.getNickname();
        String password = signupRequestDto.getPassword();
        String passwordCheck = signupRequestDto.getPasswordCheck();

        String patternNickname = "^[가-힣0-9]{3,10}$";

        // 닉네임 유효성 검사
        if (nickname == null || !Pattern.matches(patternNickname, nickname)) {
            throw new IllegalArgumentException("아이디는 한글과 숫자 3~10자리로 입력해주세요");
        }

        // 비밀번호 유효성 검사
        if (password == null || password.length() < 6 || password.length() > 12) {
            throw new IllegalArgumentException("비밀번호는 6~12자리로 입력해주세요");
        }

        // 비밀번호게 닉네임 포함 여부 유효성 검사
        if (password.contains(nickname)) {
            throw new IllformedLocaleException("비밀번호에 닉네임을 포함할 수 없습니다");
        }

        // 비밀번호 확인 유효성 검사
        if (passwordCheck == null) {
            throw new IllegalArgumentException("비밀번호를 다시 입력해주세요");
        }

        // 비밀번호 일치 확인
        if (!password.equals(passwordCheck)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다");
        }
    }
}