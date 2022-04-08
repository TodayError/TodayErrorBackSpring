package com.example.todayError.dto.UserDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class loginResponseDto {
    private Long id;
    private String nickname;

    public loginResponseDto(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }
}