package com.example.todayError.domain;

import com.example.todayError.dto.CommentDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Comment extends Timestamped {

    // ID가 자동으로 생성 및 증가합니다.
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    private Long id;

    @ManyToOne
    @Column(nullable = false)
    private Post post;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String comment;

    @Column(nullable = false)
    private Long userId;

    public Comment(CommentDto requestDto) {
        this.nickname = requestDto.getNickname();
        this.comment = requestDto.getComment();
    }

    public Comment(CommentDto requestDto, String comment) {
        this.comment = comment;
        this.nickname = requestDto.getNickname();
    }

    public Comment(CommentDto requestDto, Long userId) {
        this.nickname = requestDto.getNickname();
        this.comment = requestDto.getComment();
        this.userId = userId;
    }

    public Comment(CommentDto requestDto, Long userId, String comment) {
        this.comment = comment;
        this.nickname = requestDto.getNickname();
        this.userId = userId;
    }

    public void update(CommentDto requestDto) {
        this.comment = requestDto.getComment();
    }
}