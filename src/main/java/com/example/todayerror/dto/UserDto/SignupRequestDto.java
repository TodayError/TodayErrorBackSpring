package com.example.todayerror.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class SignupRequestDto {
    private String nickname;
    private String password;
    private String passwordCheck;
}