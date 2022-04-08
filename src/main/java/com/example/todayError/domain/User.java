package com.example.todayError.domain;

import com.example.todayError.dto.UserDto.SignupRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    public User(SignupRequestDto signupRequestDto, String encodePassword) {
        this.nickname = signupRequestDto.getNickname();
        this.password = encodePassword;
    }
}
