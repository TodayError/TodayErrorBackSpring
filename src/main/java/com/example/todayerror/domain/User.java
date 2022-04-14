package com.example.todayerror.domain;

import com.example.todayerror.dto.UserDto.SignupRequestDto;
import com.example.todayerror.validator.UserInfoValidator;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@Entity
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private Long kakaoId;

    public User(SignupRequestDto signupRequestDto, String enPassword){
        // 회원가입 입력값 유효성 검사
        UserInfoValidator.validateUserRegister(signupRequestDto);
        this.username = signupRequestDto.getNickname();
        this.password = enPassword;
    }

    public User(String nickname, String encodedPassword, Long kakaoId) {
        this.username = nickname;
        this.password = encodedPassword;
        this.kakaoId = kakaoId;
    }
}