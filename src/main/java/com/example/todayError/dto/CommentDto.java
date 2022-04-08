package com.example.todayError.dto;

import lombok.Getter;

@Getter
public class CommentDto {
    private Long postid;
    private String nickname;
    private String comment;
    private Long userId;
}
