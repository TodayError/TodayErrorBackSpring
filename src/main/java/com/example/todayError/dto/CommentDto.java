package com.example.todayError.dto;

import lombok.Getter;

@Getter
public class CommentDto {
    private Long postId;
    private String nickname;
    private String comment;
    private Long userId;
}
