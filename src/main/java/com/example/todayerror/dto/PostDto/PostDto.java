package com.example.todayerror.dto.PostDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class PostDto {

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class SaveRequest {
        private String title;
        private String content;
        private Boolean completed;
        private String category;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class PutRequest {
        private String title;
        private String content;
        private Boolean completed;
        private String category;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class DetailResponse {
        private String nickName;
        private String title;
        private String content;
        private String imageUrl;
        private String modifiedAt;
        private Boolean completed;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class MainResponse {
        private Long postId;
        private String nickName;
        private String title;
        private String category;
        private String imageUrl;
        private Boolean completed;
        private String modifiedAt;

    }
}