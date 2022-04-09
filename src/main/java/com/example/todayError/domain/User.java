package com.example.todayError.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private Long kakaoId;

    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.kakaoId = null;
    }

    public User(String nickname, String password, Long kakaoId) {
        this.nickname = nickname;
        this.password = password;
        this.kakaoId = kakaoId;
    }
}
