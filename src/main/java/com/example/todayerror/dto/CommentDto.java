package com.example.todayerror.dto;

import lombok.*;

@Getter
public class CommentDto {
    private Long postId;
    private String comment;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Response {
        private Long commentId;
        private String comment;
        private String createdAt;
        private String userName;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class commentResponse {
        private Long commentId;
        private String createdAt;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class commentUpdateResponse {
        private Long commentId;
        private String modifiedAt;
    }

}